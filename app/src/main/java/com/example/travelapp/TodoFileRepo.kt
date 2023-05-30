package com.example.travelapp

import android.content.Context
import com.example.travelapp.Todo.Companion.samples
import java.io.FileNotFoundException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class TodoFileRepo(val context: Context):TodoRepo {

    override fun load(): List<Todo> {
        try {
            val openFileInput = context.openFileInput("todos.txt")?: return samples()
            ObjectInputStream(openFileInput).use {
                return it.readObject() as ArrayList<Todo>
            }
        }
        catch (fileNotFound: FileNotFoundException){

        }
        return samples()
    }

    override fun save(items: List<Todo>) {
        val openFileInput = context.openFileOutput("todos.txt",Context.MODE_PRIVATE)?: return
        ObjectOutputStream(openFileInput).use {
            it.writeObject(items)
        }
    }

}