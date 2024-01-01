package com.example.madcamp_week01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.madcamp_week01.databinding.ContactdetailBinding

class ContactDetail(private val contact: Contacts) : Fragment() {
    private lateinit var binding: ContactdetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContactdetailBinding.inflate(inflater, container, false)
        binding.namedetail.text = contact.name
        return binding.root
    }
}