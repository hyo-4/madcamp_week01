package com.example.madcamp_week01

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.madcamp_week01.databinding.FragmentGalleryDetailBinding


class GalleryDetail(detailImage : Uri) : Fragment() {
    private lateinit var binding: FragmentGalleryDetailBinding
    var db: AppDatabase? = null
    var detailImg = detailImage
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryDetailBinding.inflate(inflater,container,false)
        db = AppDatabase.getInstance(requireContext())

        Glide.with(requireContext()).load(detailImg).into(binding.detailGalleryImage)

        binding.detailGalleryImage.setOnClickListener{
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
        return binding.root
    }

    companion object {
    }

    private fun navigateBack() {
        val myFragment = NewGallery()

        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.galleryDetailContainer, myFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}