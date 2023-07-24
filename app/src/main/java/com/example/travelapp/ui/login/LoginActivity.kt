package com.example.travelapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.travelapp.MainActivity
import com.example.travelapp.R
import com.example.travelapp.data.data.User
import com.example.travelapp.data.data.UserDatabase
import com.example.travelapp.data.data.UserRepository
import com.example.travelapp.data.data.UserRoomRepository
import com.example.travelapp.databinding.ActivityLoginBinding
import com.example.travelapp.ui.home.Recycler
import com.google.android.material.snackbar.Snackbar

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
        restoreUsersFromPreviousSession()

        binding.button.setOnClickListener { view ->
            val naam = binding.Username.text.toString()

            if(!userList.any { it.username.equals(naam)}){
                binding.textNewUser.visibility = View.VISIBLE
                binding.btnYes.visibility = View.VISIBLE
                binding.btnNo.visibility = View.VISIBLE
            }
            else if(userList.any { it.username.equals(naam)}){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name",binding.Username.text.toString())
                startActivity(intent)
            }
            else if(naam == "") {
                Snackbar.make(view, "Please enter a username and try again.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
        binding.btnYes.setOnClickListener {
            val naam = binding.Username.text.toString()
            userList.add(User(naam, null))
            userRepository.save(userList)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name",binding.Username.text.toString()+" (New User)")
            startActivity(intent)
        }

        binding.btnNo.setOnClickListener {
            binding.textNewUser.visibility = View.INVISIBLE
            binding.btnYes.visibility = View.INVISIBLE
            binding.btnNo.visibility = View.INVISIBLE
            binding.Username.text = null
        }
    }
    private fun restoreUsersFromPreviousSession() {
        userList.addAll(userRepository.load())
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        userRepository.save(userList)
    }
}