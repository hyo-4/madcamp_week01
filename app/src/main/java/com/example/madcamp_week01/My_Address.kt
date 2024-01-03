package com.example.madcamp_week01

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
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
    lateinit var SearchContact : SearchView
    var db: AppDatabase? = null
    var contactsList = mutableListOf<Contacts>()
    var filteredList = mutableListOf<Contacts>()

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
        SearchContact = view.findViewById(R.id.searchAddress)


        db = AppDatabase.getInstance(requireContext())
        CoroutineScope(Dispatchers.IO).launch{
            val savedContacts = db?.contactsDao()?.getAll() ?: emptyList()
            Log.d("savedContacts", "contacts: $savedContacts")
            var contactsList = mutableListOf<Contacts>()
            contactsList.clear()
            contactsList.addAll(savedContacts)
            filteredList.clear()
            filteredList.addAll(savedContacts)
            SearchContact.setQuery("", false)
            SearchContact.clearFocus()

            SearchContact.isFocusable = false
            SearchContact.isFocusableInTouchMode = false

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

        SearchContact.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    var newoutputList = mutableListOf<Contacts>()
//
//                    for (contact in filteredList) {
//                        if (contact.name.contains(query, true) && !newoutputList.contains(contact)) {
//                            newoutputList.add(contact)
//                        }
//                    }
//
//                    Log.d("submit", query)
//                    Log.d("filter", filteredList.toString())
//                    Log.d("new", newoutputList.toString())
//
//                    noAddressDataTextView.visibility = View.INVISIBLE
//                    recyclerV.visibility = View.VISIBLE
//
//                    adapter = AddressAdapter(newoutputList)
//                    recyclerV.adapter = adapter
//                    recyclerV.layoutManager = LinearLayoutManager(context)
//
//                    val inputMethodManager =
//                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
//                    newoutputList.clear()
//
//
//                }

                query?.let {
                    val newoutputList = filteredList.filter { contact ->
                        contact.name.contains(query, true)
                    }.toMutableList()

                    noAddressDataTextView.visibility = View.INVISIBLE
                    recyclerV.visibility = View.VISIBLE

                    adapter = AddressAdapter(newoutputList)
                    recyclerV.adapter = adapter
                    recyclerV.layoutManager = LinearLayoutManager(context)

                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)

                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("change",newText.toString())
                newText?.let {
                    val newoutputList = filteredList.filter { contact ->
                        contact.name.contains(newText, true)
                    }.toMutableList()

                    noAddressDataTextView.visibility = View.INVISIBLE
                    recyclerV.visibility = View.VISIBLE

                    adapter = AddressAdapter(newoutputList)
                    recyclerV.adapter = adapter
                    recyclerV.layoutManager = LinearLayoutManager(context)


                }
                return true
            }
        })

        val plusButton: ImageButton = view.findViewById(R.id.plusbutton)
        plusButton.setOnClickListener {
            navigateToAddContactFragment()
        }

    }

    private fun updateRecyclerView(filteredContacts: List<Contacts>) {
        if (filteredContacts.isEmpty()) {
            noAddressDataTextView.visibility = View.VISIBLE
            recyclerV.visibility = View.INVISIBLE
        } else {
            noAddressDataTextView.visibility = View.INVISIBLE
            recyclerV.visibility = View.VISIBLE

            adapter = AddressAdapter(filteredContacts)
            recyclerV.adapter = adapter
            //recyclerV.layoutManager = LinearLayoutManager(context)
            if (context != null) {
                recyclerV.setLayoutManager(LinearLayoutManager(context));
            } else {
                Log.e("YourTag", "Context is null. Unable to set the LinearLayoutManager.");
            }
        }
    }

    private fun navigateToAddContactFragment() {
        // Create an instance of the AddContact fragment
        val addContactFragment = AddContactFragment()

        // Replace the current fragment with the AddContact fragment
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, addContactFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}