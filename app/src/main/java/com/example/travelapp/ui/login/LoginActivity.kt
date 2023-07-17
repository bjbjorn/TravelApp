package com.example.travelapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.travelapp.MainActivity
import com.example.travelapp.R
import com.example.travelapp.data.data.User
import com.example.travelapp.data.data.UserRepository
import com.example.travelapp.data.data.UserRoomRepository
import com.example.travelapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userRepository : UserRepository
    private var userList = arrayListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.textNewUser.visibility = View.INVISIBLE
        binding.btnYes.visibility = View.INVISIBLE
        binding.btnNo.visibility = View.INVISIBLE
        userRepository = UserRoomRepository(applicationContext)

        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener { view ->
            val tekst = binding.Username.text.toString()
            if(binding.Username.text.toString() == "Abdullah" || binding.Username.text.toString() == "Bjorn"
                ) {
                //|| userList.contains()
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