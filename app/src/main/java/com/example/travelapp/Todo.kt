package com.example.travelapp

import java.util.ArrayList

data class Todo(
    val title: String,
    val isDone: Boolean,
    val text: String,
    val pictures: ArrayList<String>?) : java.io.Serializable{

        companion object{
            fun samples():List<Todo> = arrayListOf(
                Todo("china", true, "Default Todo item", null),
            )
        }
    }

