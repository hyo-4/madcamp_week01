package com.example.madcamp_week01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.hdodenhof.circleimageview.CircleImageView

class addcontactfragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.addcontact, container, false)

        // Find your views and set up any necessary listeners or logic
        //val addImageButton: CircleImageView = view.findViewById(R.id.addimagebutton)

        // Add any additional logic as needed

        return view
    }
}