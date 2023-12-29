package com.example.madcamp_week01

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GalleryAdapter(private val imageList: List<Int>) :
    RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        // viewType 형태 아이템 뷰를 위한 뷰홀더 객체를 생성
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // position에 해당하는 데이터를 뷰홀더 아이템뷰에 표시
        val imageUrl = imageList[position]

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener{
            showImagePopup(holder.itemView.context, imageUrl)
        }
    }

    override fun getItemCount(): Int {
        //전체 아이템 개수 리턴
        return imageList.size
    }

    private fun showImagePopup(context: Context, imageUrl: Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_image)

        val window = dialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val popupImageView: ImageView = dialog.findViewById(R.id.popupImageView)
        Glide.with(context)
            .load(imageUrl)
            .into(popupImageView)

        popupImageView.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}