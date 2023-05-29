package com.example.travelapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelapp.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}