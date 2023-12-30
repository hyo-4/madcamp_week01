package com.example.madcamp_week01

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.madcamp_week01.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                recyclerV.visibility = View.GONE
            } else {

                    noAddressDataTextView.visibility = View.GONE
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
        fragmentTransaction.replace(R.id.fragmentContainer, addContactFragment)
        fragmentTransaction.addToBackStack(null) // Optional: Adds the transaction to the back stack
        fragmentTransaction.commit()
    }
}