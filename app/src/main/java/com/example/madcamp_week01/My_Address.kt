package com.example.madcamp_week01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week01.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyAddress : Fragment() {
    lateinit var recyclerV: RecyclerView
    lateinit var noAddressDataTextView: TextView
    val TAG = "ListActivity"
    var db: AppDatabase? = null
    var contactsList = mutableListOf<Contacts>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my__address, container, false)
        recyclerV = view.findViewById(R.id.recyclerV)
        noAddressDataTextView = view.findViewById(R.id.noAddressData)

        // Initialize the Room database
        db = AppDatabase.getDatabase(requireContext())

        // Use lifecycleScope.launch to perform database operations in a coroutine
        viewLifecycleOwner.lifecycleScope.launch {
            // Use withContext to switch to the IO dispatcher for database operations
            try {
                contactsList.addAll(withContext(Dispatchers.IO) {
                    db?.contactsDao()?.getAll() ?: emptyList()
                })

                if (contactsList.isEmpty()) {
                    noAddressDataTextView.visibility = View.VISIBLE
                    recyclerV.visibility = View.GONE
                } else {
                    noAddressDataTextView.visibility = View.GONE
                    recyclerV.visibility = View.VISIBLE

                    val adapter = AddressAdapter(contactsList)
                    recyclerV.adapter = adapter
                    recyclerV.layoutManager = LinearLayoutManager(context)
                }
            } catch (e: Exception) {
                // Handle exceptions (e.g., display an error message)
                e.printStackTrace()
            }
        }

        val plusButton: ImageButton = view.findViewById(R.id.plusbutton)
        plusButton.setOnClickListener {
            navigateToAddContactFragment()
        }

        return view
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