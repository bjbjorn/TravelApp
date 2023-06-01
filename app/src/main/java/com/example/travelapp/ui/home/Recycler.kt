package com.example.travelapp.ui.home;

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

import com.example.travelapp.R
import com.example.travelapp.data.data.Post

class Recycler(val items: List<Post>) : RecyclerView.Adapter<Recycler.RecyclerViewHolder>() {

    inner class RecyclerViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.recycler, parent, false)

        view.findViewById<Button>(R.id.viewOnMaps).setOnClickListener {
            val location = view.findViewById<TextView>(R.id.textView2).text.toString()
            val url = ("https://www.google.com/maps/place/$location")
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(it.context, i, null)
        }
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val currentTodoItem = items[position]

        holder.itemView.apply {

            val checkBoxTodo = findViewById<CheckBox>(R.id.checkBox)

            findViewById<TextView>(R.id.postedBy).text = currentTodoItem.Name
            findViewById<TextView>(R.id.textView2).text = currentTodoItem.title
            findViewById<CheckBox>(R.id.checkBox).isChecked = currentTodoItem.isDone
            findViewById<TextView>(R.id.textView3).text = currentTodoItem.text
            checkBoxTodo.isChecked = currentTodoItem.isDone
            checkBoxTodo.setOnClickListener{
                currentTodoItem.isDone = checkBoxTodo.isChecked
            }
        }
    }
    override fun getItemCount(): Int = items.size
}
