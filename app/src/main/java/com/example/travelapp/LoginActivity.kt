package com.example.travelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.travelapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.textView4.visibility = View.INVISIBLE
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
                Snackbar.make(view, "WRONG", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                binding.textView4.visibility = View.VISIBLE
                binding.btnYes.visibility = View.VISIBLE
                binding.btnNo.visibility = View.VISIBLE
            }
        }
        binding.btnYes.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name",binding.Username.text.toString())
            startActivity(intent)
        }
        binding.btnNo.setOnClickListener {

            binding.textView4.visibility = View.INVISIBLE
            binding.btnYes.visibility = View.INVISIBLE
            binding.btnNo.visibility = View.INVISIBLE
            binding.Username.text = null
        }
    }
}