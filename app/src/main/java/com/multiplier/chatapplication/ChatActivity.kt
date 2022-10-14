package com.multiplier.chatapplication

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private  lateinit var chatRecyclerView: RecyclerView;
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var mDbRef: DatabaseReference

    private lateinit var messageAdapter: MessangerAdapter
    private lateinit var messageList: ArrayList<Message>

    var receiverRoom: String? = null
    var senderRoom: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat2)

        val name = intent.getStringExtra("name")
        val receverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = receverUid + senderUid
        receiverRoom = senderUid + receverUid

        supportActionBar?.title = name



        chatRecyclerView = findViewById(R.id.charRecyclerView)
        messageBox = findViewById(R.id.messagebox)
        sendButton = findViewById(R.id.sentbutton)

        messageList = ArrayList()
        messageAdapter = MessangerAdapter(this, messageList)



        chatRecyclerView.layoutManager =  LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter


        mDbRef.child("chats").child(senderRoom!!).child("messages")
                .addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        messageList.clear()
                        for(postSnapshot in snapshot.children){
                            val message = postSnapshot.getValue(Message::class.java)
                            messageList.add(message!!)
                        }
                        messageAdapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })



        sendButton.setOnClickListener{
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }


    }
}