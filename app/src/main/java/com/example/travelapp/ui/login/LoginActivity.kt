package com.example.travelapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.travelapp.MainActivity
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.textNewUser.visibility = View.INVISIBLE
        binding.btnYes.visibility = View.INVISIBLE
        binding.btnNo.visibility = View.INVISIBLE

        setContentView(binding.root)

        binding.button.setOnClickListener { view ->
            if(binding.Username.text.toString() == "Abdullah" || binding.Username.text.toString() == "Bjorn") {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name",binding.Username.text.toString())
                startActivity(intent)

            }
            else {
                binding.textNewUser.visibility = View.VISIBLE
                binding.btnYes.visibility = View.VISIBLE
                binding.btnNo.visibility = View.VISIBLE
            }
        }
        binding.btnYes.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name",binding.Username.text.toString()+" (Guest)")
            startActivity(intent)

        }
        binding.btnNo.setOnClickListener {

            binding.textNewUser.visibility = View.INVISIBLE
            binding.btnYes.visibility = View.INVISIBLE
            binding.btnNo.visibility = View.INVISIBLE
            binding.Username.text = null
        }
    }
}