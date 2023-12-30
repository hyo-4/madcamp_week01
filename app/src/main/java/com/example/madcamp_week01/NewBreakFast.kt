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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.madcamp_week01.databinding.AddcontactBinding
import com.example.madcamp_week01.databinding.FragmentFoodAddBinding
import kotlinx.coroutines.CoroutineScope

class NewBreakFast(year:Int, month:Int, Day:Int) : Fragment() {
    private lateinit var binding: FragmentFoodAddBinding
    lateinit var getBreakFast: ActivityResultLauncher<String>
    var db: AppDatabase? = null
    var FoodData = mutableListOf<Workout>()
    var inputimage: Uri? = null
    val selectedDate = "$year-$month-$Day"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFoodAddBinding.inflate(inflater, container, false)
        db = AppDatabase.getInstance(requireContext())
        Log.d("date", "$selectedDate")

        getBreakFast =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    requireActivity().contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    inputimage = it
                    binding.foodImage.setImageURI(it)
                }

                binding.selectfoodImg.setOnClickListener {
                    openImagePicker()
                }


            }
        return binding.root

    }

    private fun openImagePicker() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
        getBreakFast.launch("image/*")
    }

    private fun createContactAndNavigateBack(img: Uri?) {
        val savedContacts = db?.workoutDao()?.getAll() ?: emptyList()
        FoodData.addAll(savedContacts)

    }

    private fun navigateToCalendar() {
        // Navigate to the "my_address" fragment
        val myCalendar = Free()

        val fragmentTransaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.addFoodPage, myCalendar)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
