package com.example.madcamp_week01


import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.madcamp_week01.databinding.AddcontactBinding
import kotlinx.coroutines.CoroutineScope
import android.app.Activity
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.madcamp_week01.R
import com.example.madcamp_week01.*
import kotlinx.coroutines.launch


class NewCalendar : Fragment() {

    private lateinit var selectImageButton: Button
    private lateinit var calendarImageView : ImageView
    private lateinit var memoText: EditText
    private lateinit var calendarOptionSpinner: Spinner
    private lateinit var calendarSaveButton: Button

    private lateinit var binding: AddcontactBinding
    lateinit var getCalendar : ActivityResultLauncher<String>
    var db : AppDatabase? = null
    var WorkoutList = mutableListOf<Workout>()
    var inputimage : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = AddcontactBinding.inflate(inflater, container, false)
        db = AppDatabase.getInstance(requireContext())


        getCalendar =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    requireActivity().contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    inputimage = it
                }
            }

        binding.addphotobutton.setOnClickListener {
            openImagePicker()
        }
        binding.completebutton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                navigateToCalendar()
            }
        }
        return binding.root
    }

    private fun openImagePicker() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
        getCalendar.launch("image/*")
    }

    private fun navigateToCalendar() {
        // Navigate to the "my_address" fragment
        val myCalendar = Free()

        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.addCalendarPage, myCalendar)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

}