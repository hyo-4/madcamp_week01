package com.example.madcamp_week01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class My_Address : Fragment() {
    lateinit var recyclerV: RecyclerView
    lateinit var noAddressDataTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my__address, container, false)
        recyclerV = view.findViewById(R.id.recyclerV)
        noAddressDataTextView = view.findViewById(R.id.noAddressData)

        val addressList = loadData()

        if (addressList.isEmpty()) {
            noAddressDataTextView.visibility = View.VISIBLE
            recyclerV.visibility = View.GONE
        }
        else {
            noAddressDataTextView.visibility = View.GONE
            recyclerV.visibility = View.VISIBLE
            val adapter = AddressAdapter(loadData())
            recyclerV.adapter = adapter
            recyclerV.layoutManager = LinearLayoutManager(context)
        }
        return view
    }
    fun loadData() : List<Memo> {
        val list = mutableListOf<Memo>()
        for(i in 0..30){
            val memo = Memo(R.drawable.contact, "${i}번째 이름", "000-0000-0000")
            list.add(memo)
        }
        return list
    }


}