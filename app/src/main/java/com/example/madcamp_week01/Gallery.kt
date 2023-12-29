package com.example.madcamp_week01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class Gallery : Fragment() {

    val imageList = listOf(R.drawable.img1, R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.img6,R.drawable.img7,R.drawable.img8,R.drawable.img9,R.drawable.img10)
    val dataList = listOf(DataItem(R.drawable.img1,"new image1"),DataItem(R.drawable.img2,"new image2"),DataItem(R.drawable.img3,"new image3"),DataItem(R.drawable.img4,"new image"),DataItem(R.drawable.img5,"new image"),DataItem(R.drawable.img6,"new image"),DataItem(R.drawable.img7,"new image"),DataItem(R.drawable.img8,"new image"),DataItem(R.drawable.img9,"new image"),DataItem(R.drawable.img10,"new image"))
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
        imageCountTextView.text = "총 ${imageList.size} 개의 이미지"

        return view
    }

}