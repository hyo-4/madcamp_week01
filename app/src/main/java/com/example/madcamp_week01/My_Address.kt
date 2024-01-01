package com.example.madcamp_week01

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyAddress : Fragment() {
    private var adapter: AddressAdapter? = null
    lateinit var recyclerV: RecyclerView
    lateinit var noAddressDataTextView: TextView
    var db: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my__address, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerV = view.findViewById(R.id.recyclerV)
        noAddressDataTextView = view.findViewById(R.id.noAddressData)
        db = AppDatabase.getInstance(requireContext())
        CoroutineScope(Dispatchers.IO).launch{
            val savedContacts = db?.contactsDao()?.getAll() ?: emptyList()
            Log.d("savedContacts", "contacts: $savedContacts")
            var contactsList = mutableListOf<Contacts>()
            contactsList.addAll(savedContacts)

            if (contactsList.isEmpty()) {
                noAddressDataTextView.visibility = View.VISIBLE
                recyclerV.visibility = View.INVISIBLE
            } else {

                    noAddressDataTextView.visibility = View.INVISIBLE
                    recyclerV.visibility = View.VISIBLE

                    adapter = AddressAdapter(contactsList)
                    recyclerV.adapter = adapter
                    recyclerV.layoutManager = LinearLayoutManager(context)
            }
        }


        val plusButton: ImageButton = view.findViewById(R.id.plusbutton)
        plusButton.setOnClickListener {
            navigateToAddContactFragment()
        }
    }

    private fun navigateToAddContactFragment() {
        // Create an instance of the AddContact fragment
        val addContactFragment = AddContactFragment()

        // Replace the current fragment with the AddContact fragment
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, addContactFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}