package com.example.travelapp
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.travelapp.TodoRepo

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelapp.databinding.ActivityMain2Binding
import com.example.travelapp.ui.gallery.GalleryFragment
import com.example.travelapp.ui.slideshow.SlideshowFragment


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var todoRepository : TodoRepo
    private val todoList = arrayListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        /*var sampleTodoItems = arrayListOf(
            Todo("china", true, "very cool country lololollo", null),
        )*/
        var adapter = Recycler(todoList)
        binding.appBarMain.contentMain.activityMain.rvwTodo.adapter = adapter
        binding.appBarMain.contentMain.activityMain.rvwTodo.layoutManager =
            LinearLayoutManager(this)


        binding.appBarMain.fab.setOnClickListener { view ->
            val intent = Intent(this, addPostActivity::class.java)
            startActivity(intent)
        }
        binding.appBarMain.contentMain.activityMain.button.setOnClickListener {
            val newTodoTitle = binding.appBarMain.contentMain.activityMain.txtBar.text.toString()
            //todoList.add(Todo(newTodoTitle,false,"",null))
            todoList.add(Todo(newTodoTitle, false, "ok", null))
            //adapter.notifyItemInserted(todoList.size-1)
            adapter.notifyDataSetChanged()

        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val galleryFragment = GalleryFragment()
        supportFragmentManager.beginTransaction()
            .hide(galleryFragment)
            .commit()

        navView.setNavigationItemSelectedListener {menuItem->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    binding.appBarMain.contentMain.activityMain.rvwTodo.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction()
                        .remove(galleryFragment)
                        .commit()
                    true
                }
                R.id.nav_gallery -> {
                    binding.appBarMain.contentMain.activityMain.rvwTodo.visibility = View.INVISIBLE
                    supportFragmentManager.beginTransaction()
                        .show(galleryFragment)
                        .commit()
                    navController.navigate(R.id.nav_gallery)
                    //setContentView(R.layout.activity_gallery)
                    true
                }
                R.id.nav_slideshow->{
                    setContentView(R.layout.activity_slideshow)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity2, menu)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
       // todoRepository.save(todoList)
    }
    private fun restoreFromLast(){
        todoList.addAll(todoRepository.load())
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}