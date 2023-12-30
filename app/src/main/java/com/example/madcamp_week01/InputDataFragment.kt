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
import com.example.madcamp_week01.databinding.AddcontactBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InputDataFragment : Fragment() {

    lateinit var getResult: ActivityResultLauncher<String>
    private lateinit var binding: AddcontactBinding
    var db : AppDatabase? = null
    var contactsList = mutableListOf<Contacts>()
    var inputimage : Uri? = null

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

        binding = AddcontactBinding.inflate(inflater, container, false)
        db = AppDatabase.getInstance(requireContext())

        getResult =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    requireActivity().contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    imageView.setImageURI(uri)
                    InputImage = uri

                }
            }

        pickImage.setOnClickListener {
            openGallery()
        }

        saveButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
            navigateToGallery()}


        }

        return view
    }

    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
        getResult.launch("image/*")
    }

    private fun navigateToGallery() {

        val galleryNav = NewGallery()
        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.newImageView, galleryNav)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
