package com.example.madcamp_week01

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.madcamp_week01.databinding.ContactdetailBinding
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class ContactDetail(private val contact: Contacts) : Fragment() {
    private lateinit var binding: ContactdetailBinding
    var db : AppDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContactdetailBinding.inflate(inflater, container, false)
        db = AppDatabase.getInstance(requireContext())
        if(contact.image != null){
            Glide.with(requireContext())
                .load(contact.image)
                .into(binding.profileimg)
        }else{
            val noimage = AppCompatResources.getDrawable(requireContext(), R.drawable.blankimage)
            binding.profileimg.setImageDrawable(noimage)
        }
        binding.section.visibility = View.VISIBLE
        binding.namedetail.visibility = View.VISIBLE
        binding.profiletag.visibility = View.VISIBLE
        binding.delbutton.visibility = View.VISIBLE

        binding.namedetail.text = contact.name
        binding.profiletag.text = contact.tag
        binding.phonenum.text = contact.tel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.delbutton.setOnClickListener {
            Handler(Looper.getMainLooper()).post {
                showDeleteConfirmDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateBack()
        }
        binding.backbutton.setOnClickListener{
            navigateBack()
        }
        binding.editbutton.setOnClickListener {
            navigateEdit(contact)
        }
        binding.callbutton.setOnClickListener {
            val phoneNumber = contact.tel
            openDialer(phoneNumber)
        }
        binding.messagebutton.setOnClickListener {
            val phoneNumber = contact.tel
            openMessagingApp(phoneNumber)
        }

    }

    private fun openDialer(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")

        try {
            startActivity(dialIntent)
        } catch (e: ActivityNotFoundException) {
            // Handle exception, e.g., show a toast or log an error
            Toast.makeText(requireContext(), "Dialer not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openMessagingApp(phoneNumber: String) {
        val smsUri = Uri.parse("smsto:$phoneNumber")
        val smsIntent = Intent(Intent.ACTION_SENDTO, smsUri)

        try {
            startActivity(smsIntent)
        } catch (e: ActivityNotFoundException) {
            // Handle exception, e.g., show a toast or log an error
            Toast.makeText(requireContext(), "Messaging app not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete")
            .setMessage("\nAre you sure to delete this address?")
            .setPositiveButton("confirm") {_, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    deleteContact(contact)
                    navigateBack()
                }
            }
            .setNegativeButton("cancel", null)
            .create()

        dialog.show()
    }
    private fun deleteContact(contact: Contacts) {
        db?.contactsDao()?.delete(contact)
    }

    private fun navigateEdit(contact: Contacts) {
        val editfragment = EditProfile(contact)
        binding.section.visibility = View.INVISIBLE
        binding.namedetail.visibility = View.INVISIBLE
        binding.profiletag.visibility = View.INVISIBLE
        binding.delbutton.visibility = View.INVISIBLE
        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.profilepage, editfragment)
        fragmentTransaction.commit()
    }
    private fun navigateBack() {
        val myAddressFragment = MyAddress()
        binding.section.visibility = View.INVISIBLE
        binding.namedetail.visibility = View.INVISIBLE
        binding.profiletag.visibility = View.INVISIBLE
        binding.delbutton.visibility = View.INVISIBLE

        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.profilepage, myAddressFragment)
        fragmentTransaction.commit()
    }
}