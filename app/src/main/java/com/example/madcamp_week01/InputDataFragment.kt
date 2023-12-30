package com.example.madcamp_week01

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class InputDataFragment : Fragment() {

    lateinit var getResult: ActivityResultLauncher<String>
    val newData = mutableListOf<NewDataItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input_data, container, false)

        val imageView: ImageView = view.findViewById(R.id.imageInputView)
        val editText: EditText = view.findViewById(R.id.editInputText)
        val saveButton: Button = view.findViewById(R.id.saveImageButton)
        val pickImage: Button = view.findViewById(R.id.inputImageButton)

        val InputText = editText.text.toString()
        var InputImage : Uri

        getResult =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    requireActivity().contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    imageView.setImageURI(uri)
                    InputImage = uri
                    newData.add(NewDataItem(it, InputText))
                    Log.d("new", newData.toString())
                }
            }

        pickImage.setOnClickListener {
            openGallery()
        }

        saveButton.setOnClickListener {
            navigateToGallery()
        }

        return view
    }

    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
        getResult.launch("image/*")
    }

    private fun navigateToGallery() {
//        val galleryNav = Gallery()
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.inputContainer, galleryNav)
//            .addToBackStack(null)
//            .commit()

        val galleryNav = Gallery()
        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.inputContainer, galleryNav)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
