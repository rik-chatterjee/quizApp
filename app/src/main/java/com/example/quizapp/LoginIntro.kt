package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginIntro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null){
            Toast.makeText(this,"User is already logged in",Toast.LENGTH_SHORT).show()
            redirect("Main")
        }
        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        btnGetStarted.setOnClickListener {
            redirect("Login")
        }
    }

    private fun redirect(name: String){
        val intent = when(name){
            "Main" -> Intent(this, MainActivity::class.java)
            "Login" -> Intent(this, LoginActivity::class.java)
            else -> throw Exception("no path exists")
        }
        startActivity(intent)
        finish()
    }
}