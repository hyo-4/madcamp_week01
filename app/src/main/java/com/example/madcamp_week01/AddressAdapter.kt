package com.example.madcamp_week01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class AddressAdapter(val db: AppDatabase, var contactsList: List<Contacts>?) : RecyclerView.Adapter<AddressAdapter.Holder>() {

    lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_address_item, parent, false)
        mContext = parent.context
        return Holder(view)
    }
    override fun getItemCount(): Int {
        return contactsList!!.size
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setItem(contactsList!!.get(position), position)
    }
    fun getContact(): List<Contacts>?{
        return contactsList
    }
    inner class Holder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        var index:Int? = null
//        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
//        private val nameTextView: TextView = itemView.findViewById(R.id.nameItem)
//        private val numTextView: TextView = itemView.findViewById(R.id.numItem)
        fun setItem(contact:Contacts, position: Int){
            index = position
//            val sampleImage = AppCompatResources.getDrawable(itemView.context, R.drawable.contact)
//            imageView.setImageDrawable(sampleImage)
//            nameTextView.text = contact.name
//            numTextView.text = contact.tel
        }
    }
}

