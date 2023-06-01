package com.example.travelapp
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import com.example.travelapp.ui.home.Recycler
import com.google.android.material.internal.ViewUtils.hideKeyboard


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var postRepository : PostRepository
    private lateinit var adapter: Recycler
    private var postList = arrayListOf<Post>()
    private val galleryFragment = GalleryFragment()
    private val CAMERA_REQUEST_CODE = 101
    private val homeFragment = HomeFragment()

    @SuppressLint("RestrictedApi")
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
            val newPostTitle = binding.appBarMain.contentMain.activityMain.txtTitle.text.toString()
            val newPostText = binding.appBarMain.contentMain.activityMain.txtText.text.toString()
            postList.add(0,Post(newPostTitle, account, false, newPostText))
            adapter.notifyDataSetChanged()
            binding.appBarMain.contentMain.activityMain.txtTitle.text.clear()
            binding.appBarMain.contentMain.activityMain.txtTitle.clearFocus()
            binding.appBarMain.contentMain.activityMain.txtText.text.clear()
            binding.appBarMain.contentMain.activityMain.txtText.clearFocus()
            hideKeyboard(it)

        }
        restorePostsFromPreviousSession()
        adapter.notifyDataSetChanged()


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery
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
                    binding.appBarMain.contentMain.activityMain.txtTitle.visibility = View.VISIBLE
                    binding.appBarMain.contentMain.activityMain.txtText.visibility = View.VISIBLE
                    binding.appBarMain.contentMain.activityMain.button.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction().show(homeFragment).commit()
                    navController.navigate(R.id.nav_home)
                    true
                }
                R.id.nav_gallery -> {
                    binding.appBarMain.contentMain.activityMain.rvwPost.visibility = View.INVISIBLE
                    binding.appBarMain.contentMain.activityMain.txtTitle.visibility = View.INVISIBLE
                    binding.appBarMain.contentMain.activityMain.txtText.visibility = View.INVISIBLE
                    binding.appBarMain.contentMain.activityMain.button.visibility = View.INVISIBLE

                    supportFragmentManager.beginTransaction().show(galleryFragment).commit()
                    navController.navigate(R.id.nav_gallery)
                    true
                }
                else -> false
            }
        }
    }

    private fun restorePostsFromPreviousSession() {
        postList.addAll(postRepository.load().reversed())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            postList.clear()
            adapter.notifyDataSetChanged()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}