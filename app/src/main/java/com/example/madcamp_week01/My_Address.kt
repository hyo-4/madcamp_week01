package com.example.madcamp_week01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week01.databinding.ActivityMainBinding

class My_Address : Fragment() {
    lateinit var recyclerV: RecyclerView
    lateinit var noAddressDataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        val plusButton: ImageButton = view.findViewById(R.id.plusbutton)
        plusButton.setOnClickListener {
            navigateToAddContactFragment()
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

    private fun navigateToAddContactFragment() {
        // Create an instance of the AddContact fragment
        val addContactFragment = addcontactfragment()

        // Replace the current fragment with the AddContact fragment
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, addContactFragment)
        fragmentTransaction.addToBackStack(null) // Optional: Adds the transaction to the back stack
        fragmentTransaction.commit()
    }



}