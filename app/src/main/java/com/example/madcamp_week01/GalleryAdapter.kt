package com.example.madcamp_week01

import android.app.AlertDialog
import android.app.Dialog
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.FragmentActivity
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class GalleryAdapter(private val dataItems: MutableList<DataItem>) :
    RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView =itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        // viewType 형태 아이템 뷰를 위한 뷰홀더 객체를 생성
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // position에 해당하는 데이터를 뷰홀더 아이템뷰에 표시
        val data = dataItems[position]

        Glide.with(holder.itemView.context)
            .load(data.imageResId)
            .into(holder.imageView)

        holder.textView.text = data.text
        holder.itemView.setOnClickListener{
            showImagePopup(holder.itemView.context, data)
        }
    }

    override fun getItemCount(): Int {
        //전체 아이템 개수 리턴
        return dataItems.size
    }

    private fun showImagePopup(context: Context, dataItem: DataItem) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_image)

        val window = dialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val popupImageView: ImageView = dialog.findViewById(R.id.popupImageView)
        val popupTextView : TextView = dialog.findViewById(R.id.popupTextView)
        Glide.with(context)
            .load(dataItem.imageResId)
            .into(popupImageView)
        popupTextView.text = dataItem.text
        popupImageView.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    fun addDataItem(context: Context) {
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.add_item, null)

        val imageEditText: EditText = dialogView.findViewById(R.id.dialogImageEditText)
        val textEditText: EditText = dialogView.findViewById(R.id.dialogTextEditText)
        val addButton : Button = dialogView.findViewById(R.id.pickImageButton)



        AlertDialog.Builder(context)
            .setTitle("Add New Item")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val imageResId = imageEditText.text.toString().toIntOrNull()
                val text = textEditText.text.toString()

                if (imageResId != null && text.isNotEmpty()) {
                    val newDataItem = DataItem(imageResId, text)
                    dataItems.add(newDataItem)
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        context,
                        "Invalid input. Please enter both image ID and text.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}

