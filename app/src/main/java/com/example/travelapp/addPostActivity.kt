package com.example.travelapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.travelapp.databinding.ActivityAddPostBinding

class addPostActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        binding = ActivityAddPostBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.button2.setOnClickListener { view ->
            val todo = Todo(binding.newTitle.text.toString(), false, binding.newText.text.toString(), null)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("new todo", todo)
            startActivity(intent)

        }

    }

}