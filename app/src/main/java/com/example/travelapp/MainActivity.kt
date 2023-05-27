package com.example.travelapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.recyclerview.model.Todo
import com.example.travelapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    var sampleTodoItems = arrayListOf(
        Todo("china", true),
        Todo("get a GF °_°", false),
        Todo("Fok smash door", true),
        Todo("get good grades", false)
    )
    var adapter = Recycler(sampleTodoItems)
        binding.rvwTodo.adapter = adapter
        binding.rvwTodo.layoutManager = LinearLayoutManager(this)

        binding.button.setOnClickListener {
            val newTodoTitle = binding.txtBar.text.toString()
            sampleTodoItems.add(Todo(newTodoTitle,false))
            adapter.notifyDataSetChanged()
        }
    }
}