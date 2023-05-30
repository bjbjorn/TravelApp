package com.example.travelapp.ui.gallery

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.R
import com.example.travelapp.databinding.FragmentGalleryBinding
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class GalleryFragment : Fragment() {

private var _binding: FragmentGalleryBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
  private val CAMERA_REQUEST_CODE = 1

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

    _binding = FragmentGalleryBinding.inflate(inflater, container, false)
    val root: View = binding.root


    binding.cameraBtn.setOnClickListener {
      camera()
    }

    return root
  }

  private fun cameraCheckPermission() {
    Dexter.withContext(view?.context)
      .withPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE).withListener(

        object : MultiplePermissionsListener{
          override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
            report?.let {
              if (report.areAllPermissionsGranted()) {
                camera()
              }
            }
          }

          override fun onPermissionRationaleShouldBeShown(
            p0: MutableList<PermissionRequest>?,
            p1: PermissionToken?
          ) {
            showRotationalDialogForPermission()
          }

        }
      )
  }

  private fun camera() {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, CAMERA_REQUEST_CODE)
  }
  private fun showRotationalDialogForPermission() {
    view?.let { Snackbar.make(it, "WRONG", Snackbar.LENGTH_LONG).setAction("Action", null).show() }
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
          binding.imageView2.setImageBitmap(bitmap)
        }
      }
    }
  }

}