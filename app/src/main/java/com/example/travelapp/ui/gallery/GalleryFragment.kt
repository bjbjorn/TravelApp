package com.example.travelapp.ui.gallery

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.travelapp.R
import com.example.travelapp.data.data.User
import com.example.travelapp.data.data.UserRepository
import com.example.travelapp.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

private var _binding: FragmentGalleryBinding? = null
  private val binding get() = _binding!!
  private val CAMERA_REQUEST_CODE = 1
  private lateinit var userRepository : UserRepository
  private var userList = arrayListOf<User>()

  lateinit var imageList: List<Bitmap>
  lateinit var viewPager: ViewPager
  lateinit var viewPagerAdapter: ViewPagerAdapter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    super.onCreate(savedInstanceState)
    _binding = FragmentGalleryBinding.inflate(inflater, container, false)
    val root: View = binding.root

    viewPager = root.findViewById(R.id.idViewPager)
    imageList = ArrayList()
    viewPagerAdapter = ViewPagerAdapter(root.context, imageList)
    viewPager.adapter = viewPagerAdapter

    val requestCamera= registerForActivityResult(ActivityResultContracts.RequestPermission()) {
      if (it) {
        camera()
      } else {
        Toast.makeText(
          context,
          "Permission not granted, you can grant permission in app settings",
          Toast.LENGTH_LONG
        ).show()
      }
    }


    binding.cameraBtnMain.setOnClickListener {
      requestCamera.launch(android.Manifest.permission.CAMERA)
    }
    return root
  }


  private fun camera() {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, CAMERA_REQUEST_CODE)
  }

  override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (resultCode == Activity.RESULT_OK) {
      when(requestCode){
        CAMERA_REQUEST_CODE->{
          val bitmap = data?.extras?.get("data") as Bitmap
          imageList = imageList + bitmap
          viewPagerAdapter.addToList(bitmap)
          viewPagerAdapter.notifyDataSetChanged()
        }
      }
    }
  }

  private fun restoreUsersFromPreviousSession() {
    userList.addAll(userRepository.load())
  }
  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    userRepository.save(userList)
  }
}