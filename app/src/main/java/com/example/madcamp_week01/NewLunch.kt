package com.example.madcamp_week01

import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentTransaction
import com.example.madcamp_week01.databinding.FragmentFoodAddBinding
import kotlinx.coroutines.CoroutineScope
class NewLunch(year:Int, month:Int, Day:Int) : Fragment() {
    private lateinit var binding: FragmentFoodAddBinding
    lateinit var getLunch: ActivityResultLauncher<String>
    var db: AppDatabase? = null
    var FoodData = mutableListOf<Workout>()
    var inputimage: Uri? = null
    val selectedDate = "$year-$month-$Day"
    val addYear = year
    val addMonth = month
    val addDay = Day

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFoodAddBinding.inflate(inflater, container, false)
        db = AppDatabase.getInstance(requireContext())
        Log.d("date", "$selectedDate")

        getLunch =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    requireActivity().contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    inputimage = it
                    binding.foodImage.setImageURI(it)
                }

            }

        binding.selectfoodImg.setOnClickListener {
            openImagePicker()
        }

        binding.foodSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                createFoodAndNavigateBack(inputimage)
                navigateToCalendar()
            }
        }


        return binding.root

    }
    private fun openImagePicker() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
        getLunch.launch("image/*")
    }

    private fun createFoodAndNavigateBack(img: Uri?) {

        if(img != null){
            val newFood = Workout(
                year = addYear,
                month = addMonth,
                date = addDay,
                breakfastImg = null,
                lunchImg = img,
                dinnerImg = null,
                workoutImg = null,
                workoutTime = null,
                workoutType = null
            )
            db?.workoutDao()?.insertAll(newFood)
            FoodData.add(newFood)
            Log.d("newFood", newFood.toString())
        }


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