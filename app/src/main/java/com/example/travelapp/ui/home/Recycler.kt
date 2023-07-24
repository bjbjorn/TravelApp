package com.example.travelapp.ui.home;

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.travelapp.ImageConverter
import com.example.travelapp.Images

import com.example.travelapp.R
import com.example.travelapp.data.data.Post
import com.example.travelapp.ui.gallery.ViewPagerAdapter
import java.util.*

class Recycler(val items: List<Post>) : RecyclerView.Adapter<Recycler.RecyclerViewHolder>() {

    inner class RecyclerViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)
    private lateinit var view: View

    private val CAMERA_REQUEST_CODE = 101
    private lateinit var imageList: List<Bitmap>
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var currentPostSelected: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler, parent, false)

        view.findViewById<Button>(R.id.viewOnMaps).setOnClickListener {

            val location = view.findViewById<TextView>(R.id.countryName).text.toString()
            val url = ("https://www.google.com/maps/place/$location")
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(it.context, i, null)

        }
        view.findViewById<Button>(R.id.cameraBtnMain).setOnClickListener {
            currentPostSelected = view.findViewById<TextView>(R.id.countryName).text.toString()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
      //      intent.putExtra("post", "Denmark")
            startActivityForResult(it.context as Activity, intent, CAMERA_REQUEST_CODE, null)
        }

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val currentTodoItem = items[position]

        holder.itemView.apply {

            val checkBoxTodo = findViewById<CheckBox>(R.id.checkBox)

            findViewById<TextView>(R.id.postedBy).text = currentTodoItem.Name
            findViewById<TextView>(R.id.countryName).text = currentTodoItem.title
            checkBoxTodo.isChecked = currentTodoItem.isDone
            findViewById<TextView>(R.id.countryInfo).text = currentTodoItem.text
            checkBoxTodo.isChecked = currentTodoItem.isDone
            checkBoxTodo.setOnClickListener{
                currentTodoItem.isDone = checkBoxTodo.isChecked
            }
            val stringImageList = currentTodoItem.images
            val images = ImageConverter().toImages(stringImageList).images

            imageList = ArrayList()
            for(string in images) {
                val bitmapImage = stringToBitmap(string)
                imageList = imageList + bitmapImage
            }
            viewPager = findViewById(R.id.idViewPager)
            viewPagerAdapter = ViewPagerAdapter(context, imageList)
            viewPager.adapter = viewPagerAdapter
        }
    }

    fun addImage(image: Bitmap) {
        viewPagerAdapter.addToList(image)
    }
    override fun getItemCount(): Int = items.size
    fun notifyChange() {
        viewPagerAdapter.notifyDataSetChanged()
    }

    fun stringToBitmap(string: String?): Bitmap {
        val imageBytes = Base64.getDecoder().decode(string)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return image
    }
}

