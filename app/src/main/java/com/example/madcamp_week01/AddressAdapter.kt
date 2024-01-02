package com.example.madcamp_week01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import android.widget.Filterable

class AddressAdapter(var contactsList: List<Contacts>?) : RecyclerView.Adapter<AddressAdapter.Holder>(){

    lateinit var mContext: Context
    private var filteredList: List<Contacts>? = contactsList


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
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameItem)
        private val numTextView: TextView = itemView.findViewById(R.id.numItem)

        fun setItem(contact:Contacts, position: Int){
            index = position

            if(contact.image != null){
                Glide.with(itemView.context).load(contact.image).into(imageView)
                nameTextView.text = contact.name
                numTextView.text = contact.tel
            }else{
                val sampleImage = AppCompatResources.getDrawable(itemView.context, R.drawable.blankcontact)
                imageView.setImageDrawable(sampleImage)
                nameTextView.text = contact.name
                numTextView.text = contact.tel
            }

            itemView.setOnClickListener {
                val contactdetail = ContactDetail(contact)

                // Replace the current fragment with ContactDetailFragment
                val fragmentTransaction = (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer, contactdetail)
                fragmentTransaction.addToBackStack(null) // Optional: Adds the transaction to the back stack
                fragmentTransaction.commit()
            }
        }

    }


}

