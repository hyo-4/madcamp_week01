package com.example.madcamp_week01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class AddressAdapter(var memoList:List<Memo>) : RecyclerView.Adapter<Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_address_item, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int {
        return memoList.size
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = memoList[position]
        holder.setItem(memo)
    }
}

class Holder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val nameTextView: TextView = itemView.findViewById(R.id.nameItem)
    private val numTextView: TextView = itemView.findViewById(R.id.numItem)
    fun setItem(memo:Memo){
        val sampleImage = AppCompatResources.getDrawable(itemView.context, R.drawable.contact)
        imageView.setImageDrawable(sampleImage)
        nameTextView.text = memo.name
        numTextView.text = memo.number
    }
}