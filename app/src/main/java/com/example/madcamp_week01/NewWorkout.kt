package com.example.madcamp_week01

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentTransaction
import com.example.madcamp_week01.databinding.FragmentFoodAddBinding
import com.example.madcamp_week01.databinding.FragmentWorkoutAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewWorkout(year:Int, month:Int, Day:Int) : Fragment() {

    private lateinit var binding: FragmentWorkoutAddBinding
    lateinit var getWorkout: ActivityResultLauncher<String>
    var db: AppDatabase? = null
    var WorkoutData = mutableListOf<Workout>()
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
        binding = FragmentWorkoutAddBinding.inflate(inflater, container, false)
        db = AppDatabase.getInstance(requireContext())
        Log.d("date", "$selectedDate")

        getWorkout =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
//                    requireActivity().contentResolver.takePersistableUriPermission(
//                        it,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    )
                    inputimage = it
                    binding.workoutImage.setImageURI(it)
                }

            }

        binding.selectworkout.setOnClickListener {
            openImagePicker()
        }

        binding.workoutSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                createWorkoutAndNavigateBack(inputimage)
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
        getWorkout.launch("image/*")
    }

    private fun createWorkoutAndNavigateBack(img: Uri?) {

        val worktype = binding.workTypeText.text.toString()
        val worktime = binding.WorkTimeText.text.toString()
        val dateWorkout = db?.workoutDao()?.getWorkoutByDate(addYear, addMonth, addDay)
        var selectedWorkout: Workout? = dateWorkout?.firstOrNull()

        if(worktime.isNotEmpty() && worktime.isNotEmpty()){
            val newWorkout = Workout(
                year = addYear,
                month = addMonth,
                date = addDay,
                breakfastImg = selectedWorkout?.breakfastImg,
                lunchImg = selectedWorkout?.lunchImg,
                dinnerImg = selectedWorkout?.dinnerImg,
                workoutImg = img,
                workoutTime = worktime,
                workoutType = worktype
            )
            db?.workoutDao()?.insertOrUpdate(newWorkout)
            WorkoutData.add(newWorkout)
            Log.d("newFood", newWorkout.toString())
        }else{

        }


    }

    private fun navigateToCalendar() {
        // Navigate to the "my_address" fragment
        val myCalendar = Free()

        val fragmentTransaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.addWorkoutPage, myCalendar)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun CheckDataBase(){
        val savedWorkout = db?.workoutDao()?.getAll()?: emptyList()
        Log.d("savedWorkout", savedWorkout.toString())
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}