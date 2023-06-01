package com.example.travelapp
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelapp.data.data.Post
import com.example.travelapp.data.data.PostRepository
import com.example.travelapp.data.data.PostRoomRepository
import com.example.travelapp.databinding.ActivityMain2Binding
import com.example.travelapp.ui.gallery.GalleryFragment
import com.example.travelapp.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var postRepository : PostRepository
    private lateinit var adapter: Recycler
    private val postList = arrayListOf<Post>()
    private val galleryFragment = GalleryFragment()
    private val CAMERA_REQUEST_CODE = 101
    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        postRepository = PostRoomRepository(applicationContext)

        val extras = intent.extras
        val account = extras?.get("name") as String

        adapter = Recycler(postList)


        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.contentMain.activityMain.rvwPost.adapter = adapter
        binding.appBarMain.contentMain.activityMain.rvwPost.layoutManager = LinearLayoutManager(this)


        binding.appBarMain.contentMain.activityMain.button.setOnClickListener {
            val newPostTitle = binding.appBarMain.contentMain.activityMain.txtBar.text.toString()
            postList.add(Post(newPostTitle, false, "texttexttexttext"))
            //adapter.notifyItemInserted(todoList.size-1)
            adapter.notifyDataSetChanged()
            binding.appBarMain.contentMain.activityMain.txtBar.text.clear()
            binding.appBarMain.contentMain.activityMain.txtBar.clearFocus()

        }
        restorePostsFromPreviousSession()
        adapter.notifyDataSetChanged()


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.getHeaderView(0).findViewById<TextView>(R.id.nav_account_name).text = account


        navView.setNavigationItemSelectedListener {menuItem->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    binding.appBarMain.contentMain.activityMain.rvwPost.visibility = View.VISIBLE
                    binding.appBarMain.contentMain.activityMain.txtBar.visibility = View.VISIBLE
                    binding.appBarMain.contentMain.activityMain.button.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction().show(homeFragment).commit()
                    navController.navigate(R.id.nav_home)
                    true
                }
                R.id.nav_gallery -> {
                    binding.appBarMain.contentMain.activityMain.rvwPost.visibility = View.INVISIBLE
                    binding.appBarMain.contentMain.activityMain.txtBar.visibility = View.INVISIBLE
                    binding.appBarMain.contentMain.activityMain.button.visibility = View.INVISIBLE

                    supportFragmentManager.beginTransaction().show(galleryFragment).commit()
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

    private fun restorePostsFromPreviousSession() {
        postList.addAll(postRepository.load())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity2, menu)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        postRepository.save(postList)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when(requestCode){
                CAMERA_REQUEST_CODE->{

                    val bitmap = data?.extras?.get("data") as Bitmap
                    val foto = findViewById<ImageView>(R.id.idViewPager)
                    foto.setImageBitmap(bitmap)
                }
            }
        }
    }



}