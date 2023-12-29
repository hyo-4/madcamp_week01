package com.example.madcamp_week01

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class Gallery : Fragment() {

    val dataList = mutableListOf(DataItem(R.drawable.img1,"new image1"),DataItem(R.drawable.img2,"new image2"),DataItem(R.drawable.img3,"new image3"),DataItem(R.drawable.img4,"new image"),DataItem(R.drawable.img5,"new image"),DataItem(R.drawable.img6,"new image"),DataItem(R.drawable.img7,"new image"),DataItem(R.drawable.img8,"new image"),DataItem(R.drawable.img9,"new image"),DataItem(R.drawable.img10,"new image"))
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

        addButton.setOnClickListener{
            adapter.addDataItem(requireContext())
        }

        return view
    }


}