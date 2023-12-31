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
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.madcamp_week01.databinding.AddcontactBinding
import com.example.madcamp_week01.databinding.FragmentFoodAddBinding
import kotlinx.coroutines.CoroutineScope

class NewDinnner(year:Int, month:Int, Day:Int) : Fragment() {

    private lateinit var binding: FragmentFoodAddBinding
    lateinit var getDinner: ActivityResultLauncher<String>
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

        getDinner =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
//                    requireActivity().contentResolver.takePersistableUriPermission(
//                        it,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    )
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
                CheckDataBase()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateToCalendar()
        }

        return binding.root

    }

    private fun openImagePicker() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
        getDinner.launch("image/*")
    }

    private fun createFoodAndNavigateBack(img: Uri?) {
        val dateWorkout = db?.workoutDao()?.getWorkoutByDate(addYear, addMonth, addDay)
        var selectedWorkout: Workout? = dateWorkout?.firstOrNull()
        if(img != null){
            val newFood = Workout(
                year = addYear,
                month = addMonth,
                date = addDay,
                breakfastImg = selectedWorkout?.breakfastImg,
                lunchImg = selectedWorkout?.lunchImg,
                dinnerImg = img,
                workoutImg = selectedWorkout?.workoutImg,
                workoutTime = selectedWorkout?.workoutTime,
                workoutType = selectedWorkout?.workoutType
            )
            db?.workoutDao()?.insertOrUpdate(newFood)
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


    private fun CheckDataBase(){
        val savedFood = db?.workoutDao()?.getAll()?: emptyList()
        Log.d("savedFood", savedFood.toString())
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}