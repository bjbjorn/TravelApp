package com.example.travelapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelapp.MainActivity
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.button.setOnClickListener { view ->
            if(binding.Password.text.toString() == "xxx") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else {
                Snackbar.make(view, "WRONG", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }
}