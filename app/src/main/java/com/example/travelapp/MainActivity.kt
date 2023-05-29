package com.example.travelapp
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.recyclerview.model.Todo
import com.example.travelapp.databinding.ActivityMain2Binding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var galleryBinding : GalleryActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        var sampleTodoItems = arrayListOf(
            Todo("china", true, "very cool country lololollo", null),
        )
        var adapter = Recycler(sampleTodoItems)
        binding.appBarMain.contentMain.activityMain.rvwTodo.adapter = adapter
        binding.appBarMain.contentMain.activityMain.rvwTodo.layoutManager =
            LinearLayoutManager(this)


        binding.appBarMain.fab.setOnClickListener { view ->
            val intent = Intent(this, addPostActivity::class.java)
            startActivity(intent)
        }
        binding.appBarMain.contentMain.activityMain.button.setOnClickListener {
            val newTodoTitle = binding.appBarMain.contentMain.activityMain.txtBar.text.toString()
            sampleTodoItems.add(Todo(newTodoTitle, false, "ok", null))
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

        navView.setNavigationItemSelectedListener {
            when (R.id.nav_home) {
                R.id.nav_home -> {
                    //setContentView(R.layout.activity_gallery)
                    val intent = Intent(this, GalleryActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}