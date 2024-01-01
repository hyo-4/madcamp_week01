package com.example.madcamp_week01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.madcamp_week01.databinding.ContactdetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        binding.namedetail.text = contact.name
        binding.phonenum.text = contact.tel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.delbutton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                deleteContact(contact)
                navigateBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateBack()
        }
    }
    private fun deleteContact(contact: Contacts) {
        db?.contactsDao()?.delete(contact)
    }
    private fun navigateBack() {
        val myAddressFragment = MyAddress()
        binding.section.visibility = View.INVISIBLE
        binding.namedetail.visibility = View.INVISIBLE
        binding.profiletag.visibility = View.INVISIBLE

        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.profilepage, myAddressFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}