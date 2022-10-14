package com.multiplier.chatapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtSignup: LinearLayout
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()

        supportActionBar?.hide()


        edtUsername = findViewById(R.id.signup_user_name)
        edtPassword = findViewById(R.id.signup_password)
        edtName = findViewById(R.id.signup_name)
        edtSignup = findViewById(R.id.signup_button)



        edtSignup.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtUsername.text.toString()
            val pass = edtPassword.text.toString()

            if (name.equals("")){
                Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show()
            } else if (email.equals("")){
                Toast.makeText(this, "Please enter your email id.", Toast.LENGTH_SHORT).show()
            } else if (pass.equals("")){
                Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show()
            } else {
                signup(name, email, pass)
            }
        }

    }

    private fun signup(name: String, email1: String, pass1: String) {
        // logic of creating user
        mAuth.createUserWithEmailAndPassword(email1, pass1)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code of jumping to home
                    adUserToDatabase(name, email1, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Some error occurred.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun adUserToDatabase(name: String, email1: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email1, uid))
    }


}