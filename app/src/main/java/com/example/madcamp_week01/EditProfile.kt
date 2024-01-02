package com.example.madcamp_week01

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Spinner
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.madcamp_week01.databinding.EditprofileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfile(private val contact: Contacts) : Fragment(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: EditprofileBinding
    var db : AppDatabase? = null
    lateinit var getPhoto : ActivityResultLauncher<String>
    var inputimage : Uri? = contact.image
    var selectedOption : String? = null
    var contactsList = mutableListOf<Contacts>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditprofileBinding.inflate(inflater, container, false)
        db = AppDatabase.getInstance(requireContext())

        binding.editphotobutton.visibility = View.VISIBLE
        if(contact.image != null){
            Glide.with(requireContext())
                .load(contact.image)
                .into(binding.addimagebutton)
        } else {
            val noimage = AppCompatResources.getDrawable(requireContext(), R.drawable.blankcontact)
            binding.addimagebutton.setImageDrawable(noimage)
        }
        binding.editname.setText(contact.name)
        binding.editnumber.setText(contact.tel)
        binding.spinner.setSelection(getPosition(binding.spinner, contact.tag))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhoto =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    inputimage = it
                    binding.addimagebutton.setImageURI(it)
                }
            }

        binding.editphotobutton.setOnClickListener {
            showPopup(binding.editphotobutton)
        }

        binding.completebutton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                editContact(inputimage)
                navigateToMyAddressFragment()
            }
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedOption = parent.getItemAtPosition(position) as String
                // Update the data based on selected option and refresh the RecyclerView
                Log.d("selected", "$selectedOption")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: Do something when nothing is selected
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateToContactDetail(contact)
        }
    }

    private fun navigateToContactDetail(contact: Contacts){
        val contactdetail = ContactDetail(contact)
        binding.editphotobutton.visibility = View.INVISIBLE

        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.editprofile, contactdetail)
        fragmentTransaction.commit()
    }

    private fun editContact(img:Uri?) {
        val savedContacts = db?.contactsDao()?.getAll() ?: emptyList()
        contactsList.addAll(savedContacts)
        val name = binding.editname.text.toString()
        val receivedTag = selectedOption!!
        val phoneNumber = binding.editnumber.text.toString()
        if ((selectedOption != null) && name.isNotEmpty() && phoneNumber.isNotEmpty()) {
            // Create a Contacts object with the provided data
            val newcontact = Contacts(
                id = contact.id,
                image = img,
                name = name,
                tag = receivedTag,
                tel = phoneNumber
            )

            db?.contactsDao()?.update(newcontact)
            contactsList.remove(contact)
            contactsList.add(newcontact)
        } else {
            // Handle validation error (e.g., show a message)
        }
    }

    private fun openImagePicker() {
        getPhoto.launch("image/*")
    }

    private fun getPosition(spinner: Spinner, item: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == item) {
                return i
            }
        }
        return 0
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(R.menu.editphoto, popup.menu)
        popup.setOnMenuItemClickListener(this) // Set the listener to 'this'
        popup.show()
    }

    private fun navigateToMyAddressFragment() {
        // Navigate to the "my_address" fragment
        val myAddressFragment = MyAddress()
        binding.editphotobutton.visibility = View.INVISIBLE
        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.editprofile, myAddressFragment)
        fragmentTransaction.commit()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.galleryoption -> {
                // Handle gallery option
                // Call a method or perform actions when the gallery option is selected
                Log.d("galleryoption", "gallery option is clicked")
                openImagePicker()
                return true
            }

            R.id.defaultoption -> {
                // Handle default option
                // Call a method or perform actions when the default option is selected
                Log.d("defaultoption", "default option is clicked")
                val noimage = AppCompatResources.getDrawable(requireContext(), R.drawable.blankcontact)
                binding.addimagebutton.setImageDrawable(noimage)
                inputimage = null
                return true
            }
            else -> return false
        }
    }
}