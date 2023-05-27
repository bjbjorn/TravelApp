package be.kuleuven.recyclerview.model

import java.util.ArrayList

data class Todo(val title: String, val isDone: Boolean, val text: String, val pictures: ArrayList<String>?) : java.io.Serializable