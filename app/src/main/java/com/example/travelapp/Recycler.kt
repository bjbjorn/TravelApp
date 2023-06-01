package com.example.travelapp;

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.databinding.RecyclerBinding

import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
class Recycler(val items: List<Todo>) : RecyclerView.Adapter<Recycler.RecyclerViewHolder>() {

    inner class RecyclerViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)
    private lateinit var binding: RecyclerBinding
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: List<Int>
    lateinit var mainActivity: MainActivity
    private val httpClient = OkHttpClient()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.recycler, parent, false)

        val foto = view.findViewById(R.id.idViewPager2) as ImageView
        foto.setImageResource(R.drawable.gerard)


        view.findViewById<Button>(R.id.viewOnMaps).setOnClickListener {
            val location = view.findViewById<TextView>(R.id.textView2).text.toString()
            val url = ("https://www.google.com/maps/place/$location")
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(it.context, i, null)
        }

        view.findViewById<Button>(R.id.addOnePhoto).setOnClickListener {
        }
        return RecyclerViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        // bind the data to our items: set the todo text view text and checked state accordingly
        val currentTodoItem = items[position]

        holder.itemView.apply {
            //checkbox blijft aangevinkt
            //maar bij rerun nog niet opgeslagen, maar dus wel progression

            val checkBoxTodo = findViewById<CheckBox>(R.id.checkBox)

            findViewById<TextView>(R.id.textView2).text = currentTodoItem.title
            findViewById<CheckBox>(R.id.checkBox).isChecked = currentTodoItem.isDone
            findViewById<TextView>(R.id.textView3).text = currentTodoItem.text
            //checkBoxTodo.isChecked = currentTodoItem.isDone
            checkBoxTodo.isChecked = currentTodoItem.isDone
            checkBoxTodo.setOnClickListener{
                currentTodoItem.isDone = checkBoxTodo.isChecked
            }
        }


    }

    override fun getItemCount(): Int = items.size


/*
fun imageChooser() {
    val i = Intent(Intent.ACTION_GET_CONTENT)
    i.setType("image/*")
    startActivity(Intent.createChooser(i))
}
*/

 */
}
