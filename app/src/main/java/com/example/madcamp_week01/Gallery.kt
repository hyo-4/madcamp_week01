package com.example.madcamp_week01

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class Gallery : Fragment() {

    lateinit var getResult: ActivityResultLauncher<String>
    private lateinit var galleryAdapter: GalleryAdapter
    val dataList = mutableListOf(DataItem(R.drawable.img1,"new image1"),DataItem(R.drawable.img2,"new image2"),DataItem(R.drawable.img3,"new image3"),DataItem(R.drawable.img4,"new image"),DataItem(R.drawable.img5,"new image"),DataItem(R.drawable.img6,"new image"),DataItem(R.drawable.img7,"new image"),DataItem(R.drawable.img8,"new image"),DataItem(R.drawable.img9,"new image"),DataItem(R.drawable.img10,"new image"))
    val newDataList = mutableListOf<NewDataItem>()
    private val AllofList = mutableListOf<Any>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)


        val adapter = GalleryAdapter(dataList)
        recyclerView.adapter = adapter

        val imageCountTextView: TextView = view.findViewById(R.id.imageCountTextView)
        imageCountTextView.text = "총 ${dataList.size} 개의 이미지"

        val addButton : Button = view.findViewById(R.id.pickImageButton)

        getResult =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    requireActivity().contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    newDataList.add(NewDataItem(it, "New Image"))
                    AllofList.add(newDataList.last())
                    Log.d("new", newDataList.toString())
                }
            }


        addButton.setOnClickListener{
            //adapter.addDataItem(requireContext())
            navigateToInputDataFragment()
        }

        return view
    }

    private fun navigateToInputDataFragment() {
        val inputDataFragment = InputDataFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentGallery, inputDataFragment)
            .addToBackStack(null)
            .commit()
    }


    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
        getResult.launch("image/*")
    }

    fun addNewData(newDataItem: NewDataItem) {
        AllofList.add(newDataItem)
        Log.d("gallery 저장 ", AllofList.toString())
        galleryAdapter.notifyDataSetChanged()
    }

}