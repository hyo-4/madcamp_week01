package com.example.madcamp_week01

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewGallery : Fragment() {
    private var newGalleryAdapter: NewGalleryAdapter? = null
    lateinit var GalleryRecyclerView: RecyclerView
    lateinit var GalleryText: TextView
    var db: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_newgallery, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GalleryRecyclerView = view.findViewById(R.id.recyclerNewGalleryView)
        GalleryText = view.findViewById(R.id.noGalleryData)
        val imageCount : TextView = view.findViewById(R.id.newGalleryimageCount)

        db = AppDatabase.getInstance(requireContext())
        CoroutineScope(Dispatchers.IO).launch{
            val savedContacts = db?.contactsDao()?.getAll() ?: emptyList()
            Log.d("savedContacts", "contacts: $savedContacts")
            var contactsList = mutableListOf<Contacts>()
            contactsList.addAll(savedContacts)

            if (contactsList.isEmpty()) {
                GalleryText.visibility = View.VISIBLE
                GalleryRecyclerView.visibility = View.GONE
            } else {

                GalleryText.visibility = View.GONE
                GalleryRecyclerView.visibility = View.VISIBLE

                newGalleryAdapter = NewGalleryAdapter(contactsList)
                imageCount.text = "${contactsList.size}개의 이미지"
                GalleryRecyclerView.adapter = newGalleryAdapter
                GalleryRecyclerView.layoutManager = GridLayoutManager(context,2)
            }
        }


        val plusButton: Button = view.findViewById(R.id.newGalleryImageButton)
        plusButton.setOnClickListener {
            navigateToAddContactFragment()
        }
    }

    private fun navigateToAddContactFragment() {
        // Create an instance of the AddContact fragment
        val inputDataFragment = InputDataFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentNewGallery, inputDataFragment)
            .addToBackStack(null)
            .commit()
    }
}