package com.example.travelapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelapp.data.data.Post
import com.example.travelapp.data.data.PostRepository
import com.example.travelapp.data.data.PostRoomRepository
import com.example.travelapp.databinding.ActivityMain2Binding
import com.example.travelapp.ui.gallery.GalleryFragment
import com.example.travelapp.ui.home.HomeFragment
import com.example.travelapp.ui.home.Recycler
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var activityMainBinding: com.example.travelapp.databinding.ActivityMainBinding
    private lateinit var postRepository : PostRepository
    private lateinit var adapter: Recycler
    private var postList = arrayListOf<Post>()
    private val CAMERA_REQUEST_CODE = 101
    private val homeFragment = HomeFragment()
    private val countries = arrayOf("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda",
        "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
        "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
        "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic",
        "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the",
        "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica",
        "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)",
        "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia",
        "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti",
        "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia",
        "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati",
        "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia",
        "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of",
        "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico",
        "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru",
        "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island",
        "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn",
        "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia",
        "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone",
        "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands",
        "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland",
        "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga",
        "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates",
        "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",
        "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine")


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        activityMainBinding = binding.appBarMain.contentMain.activityMain
        setContentView(binding.root)
        postRepository = PostRoomRepository(applicationContext)

        val extras = intent.extras
        val account = extras?.get("name") as String

        adapter = Recycler(postList)

        val galleryFragment = GalleryFragment()

        setSupportActionBar(binding.appBarMain.toolbar)
        activityMainBinding.rvwPost.adapter = adapter
        binding.appBarMain.contentMain.activityMain.rvwPost.layoutManager = LinearLayoutManager(this)

        val textAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)
        activityMainBinding.txtCountry.setAdapter(textAdapter)

        activityMainBinding.button.setOnClickListener {
            val newPostTitle = activityMainBinding.txtCountry.text.toString()
            val newPostText = activityMainBinding.txtInfo.text.toString()
            if(!countries.contains(newPostTitle)) {
                Snackbar.make(findViewById(R.id.activity_main) ,"Please enter an existing country.",
                    Snackbar.LENGTH_LONG).setAction("Action",null).show()
            }
            else if(postList.any {it.title.equals(newPostTitle)
                        || it.Name.equals(findViewById(R.id.Username))}){
                Snackbar.make(findViewById(R.id.activity_main) ,"This post already exists, check it out!",
                    Snackbar.LENGTH_LONG).setAction("Action",null).show()
            }
            else {
                postList.add(0, Post(newPostTitle, account, false, newPostText, null))
                adapter.notifyDataSetChanged()
                activityMainBinding.txtCountry.text.clear()
                activityMainBinding.txtCountry.clearFocus()
                activityMainBinding.txtInfo.text.clear()
                activityMainBinding.txtInfo.clearFocus()
                hideKeyboard(it)
            }

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
                    activityMainBinding.rvwPost.visibility = View.VISIBLE
                    activityMainBinding.txtCountry.visibility = View.VISIBLE
                    activityMainBinding.txtInfo.visibility = View.VISIBLE
                    activityMainBinding.button.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction().show(homeFragment).commit()
                    navController.navigate(R.id.nav_home)
                    true
                }
                R.id.nav_gallery -> {
                    activityMainBinding.rvwPost.visibility = View.INVISIBLE
                    activityMainBinding.txtCountry.visibility = View.INVISIBLE
                    activityMainBinding.txtInfo.visibility = View.INVISIBLE
                    activityMainBinding.button.visibility = View.INVISIBLE

                    val bundle = Bundle()
                    bundle.putString("account", account)
                    galleryFragment.arguments = bundle
                    val fragmentTransaction = supportFragmentManager.beginTransaction()

            //        galleryFragment.account = account

                    fragmentTransaction.show(galleryFragment).commit()

                    navController.navigate(R.id.nav_gallery)
                    true
                }
                else -> false
            }
        }

        val requestCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {

            } else {
                Toast.makeText(
                    this,
                    "Permission not granted, you can grant permission in app settings",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        requestCamera.launch(android.Manifest.permission.CAMERA)
    }

    private fun restorePostsFromPreviousSession() {
        postList.addAll(postRepository.load())
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when(requestCode){
                CAMERA_REQUEST_CODE->{

                    val bitmap = data?.extras?.get("data") as Bitmap
                    val bitmapString = bitmapToString(bitmap)
                    for(post in postList){
                        if(post.title == adapter.currentPostSelected) {
                            var imageList = post.images
                            val images = ImageConverter().toImages(imageList).images.toMutableList()
                            images += bitmapString
                            val imageClass = Images()
                            imageClass.images = images
                            post.images = ImageConverter().toString(imageClass)
                        }
                    }
                    adapter.notifyDataSetChanged()
                    adapter.notifyChange()
                }
            }
        }
    }
    fun bitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }
}