package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            login()
        }

        val btnSignUp = findViewById<TextView>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(){
        val email = findViewById<EditText>(R.id.etEmailAddress).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()

        if( email.isBlank() || password.isBlank()){
            Toast.makeText(this,"Email/password cannot be empty",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}