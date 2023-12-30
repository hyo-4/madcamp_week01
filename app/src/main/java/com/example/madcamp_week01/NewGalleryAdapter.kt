package com.example.madcamp_week01

import android.app.Dialog
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide

class NewGalleryAdapter(var contactsList: List<Contacts>?) : RecyclerView.Adapter<NewGalleryAdapter.ContactViewHolder>() {

    lateinit var galleryContext: Context

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var index: Int? = null
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(contact: Contacts, position: Int) {
            index = position

            if (contact.image != null) {
                // Load image using Glide
                Glide.with(itemView.context)
                    .load(contact.image)
                    .into(imageView)
            } else {
                val sampleImage = AppCompatResources.getDrawable(itemView.context, R.drawable.blankcontact)
                imageView.setImageDrawable(sampleImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        galleryContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
       holder.bind(contactsList!![position], position)
    }

    override fun getItemCount(): Int {
        return contactsList?.size ?: 0
    }
}