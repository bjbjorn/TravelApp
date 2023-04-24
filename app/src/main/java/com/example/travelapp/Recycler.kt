package com.example.travelapp;

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import be.kuleuven.recyclerview.model.Todo
import com.example.travelapp.databinding.RecyclerBinding

import androidx.viewpager.widget.ViewPager
import com.gtappdevelopers.kotlingfgproject.ViewPagerAdapter

class Recycler(val items: List<Todo>) : RecyclerView.Adapter<Recycler.RecyclerViewHolder>() {

    inner class RecyclerViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)
    private lateinit var binding: RecyclerBinding
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: List<Int>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler, parent, false)

        viewPager = view.findViewById(R.id.idViewPager)
        imageList = ArrayList<Int>()
        imageList = imageList + R.drawable.bijnapasen
        imageList = imageList + R.drawable.gerard
        viewPagerAdapter = ViewPagerAdapter(view.findViewById<TextView>(R.id.textView2).context, imageList)
        viewPager.adapter = viewPagerAdapter



        view.findViewById<Button>(R.id.viewOnMaps).setOnClickListener {
            val location = view.findViewById<TextView>(R.id.textView2).text.toString()
            val url = ("https://www.google.com/maps/place/$location")
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(it.context, i, null)
        }

        view.findViewById<Button>(R.id.addPhoto).setOnClickListener {
            imageChooser();
        }
        return RecyclerViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        // bind the data to our items: set the todo text view text and checked state accordingly
        val currentTodoItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.textView2).text = currentTodoItem.title
            findViewById<CheckBox>(R.id.checkBox).isChecked = currentTodoItem.isDone
        }


    }

    override fun getItemCount(): Int = items.size

    fun imageChooser() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.setType("image/*")
        startActivity(Intent.createChooser(i))
    }

}
