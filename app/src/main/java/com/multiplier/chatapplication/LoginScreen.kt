package com.multiplier.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtLogin: LinearLayout
    private lateinit var edtSignup: LinearLayout
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        mAuth=FirebaseAuth.getInstance()
        supportActionBar?.hide()

        edtUsername = findViewById(R.id.login_user_name)
        edtPassword = findViewById(R.id.login_password)
        edtLogin = findViewById(R.id.login_button)
        edtSignup = findViewById(R.id.signup_button)

        edtSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        edtLogin.setOnClickListener {
            val email = edtUsername.text.toString()
            val pass = edtPassword.text.toString()

            if (email.equals("")){
                Toast.makeText(this, "Please enter your email id.", Toast.LENGTH_SHORT).show()
            } else if (pass.equals("")){
                Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show()
            } else {
                login(email, pass)
            }
        }

    }



    private  fun login(email: String, password:String){
        // logic for lo0gging user


        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code of jumping to home
                    val intent = Intent(this@LoginScreen, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()

                }
            }

    }
}