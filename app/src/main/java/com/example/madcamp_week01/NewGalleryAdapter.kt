package com.example.madcamp_week01

import android.app.Dialog
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
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
        val textView: TextView =itemView.findViewById(R.id.textView)

        fun bind(contact: Contacts, position: Int) {
            index = position

            if (contact.image != null) {
                // Load image using Glide
                textView.text = contact.name
                Glide.with(itemView.context)
                    .load(contact.image)
                    .into(imageView)
                imageView.setOnClickListener {
                    showImagePopup(contact.name, contact.image!!)
                }
            } else {
                val sampleImage = AppCompatResources.getDrawable(itemView.context, R.drawable.blankcontact)
                imageView.setImageDrawable(sampleImage)
            }
        }

        private fun showImagePopup(name: String, imageUri: Uri) {
            // Create a dialog with a custom layout
            val dialog = Dialog(itemView.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.popup_image)

            val popupImageView: ImageView = dialog.findViewById(R.id.popupImageView)
            val popupTextView: TextView = dialog.findViewById(R.id.popupTextView)

            // Load image and set text
            Glide.with(itemView.context)
                .load(imageUri)
                .into(popupImageView)
            popupTextView.text = name

            // Close the popup when clicked
            popupImageView.setOnClickListener {
                dialog.dismiss()
            }

            // Make the dialog background transparent
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // Show the dialog
            dialog.show()
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