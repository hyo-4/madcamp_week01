package com.example.madcamp_week01

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewGallery : Fragment() {
    private var newGalleryAdapter: NewGalleryAdapter? = null
    lateinit var TitleView : TextView
    lateinit var GalleryRecyclerView: RecyclerView
    lateinit var GalleryText: TextView
    lateinit var DietBtn : ImageButton
    lateinit var ExerciseBtn : ImageButton
    var db: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_newgallery, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TitleView = view.findViewById(R.id.textView2)
        GalleryRecyclerView = view.findViewById(R.id.recyclerNewGalleryView)
        GalleryText = view.findViewById(R.id.noGalleryData)
        DietBtn = view.findViewById(R.id.btnFilterDiet)
        ExerciseBtn = view.findViewById(R.id.btnFilterExercise)

        TitleView.setText("Gallery")


        db = AppDatabase.getInstance(requireContext())
        CoroutineScope(Dispatchers.IO).launch{
            val savedContacts = db?.workoutDao()?.getAll() ?: emptyList()
            Log.d("savedContacts", "contacts: $savedContacts")
            var WorkoutList = mutableListOf<Workout>()
            WorkoutList.addAll(savedContacts)

            if (WorkoutList.isEmpty()) {
                GalleryText.visibility = View.VISIBLE
                GalleryRecyclerView.visibility = View.GONE
            } else {

                GalleryText.visibility = View.GONE
                GalleryRecyclerView.visibility = View.VISIBLE

                newGalleryAdapter = NewGalleryAdapter(WorkoutList)
                GalleryRecyclerView.adapter = newGalleryAdapter
                GalleryRecyclerView.layoutManager = LinearLayoutManager(context)

            }

            DietBtn.setOnClickListener{
                //workoutList 자체에 접근하면 그 데이터가 수정되기 때문에 filteredList를 새로 생성해서 workoutList에 복사본을 만들고, 그 복사본에 필요한 img 빼고는 null로 바꾼다.
                val filteredList = mutableListOf<Workout>()
                TitleView.setText("Diet Gallery")
                for (workout in WorkoutList) {

                    if (workout.breakfastImg != null || workout.lunchImg != null || workout.dinnerImg != null) {
                        val filteredWorkout = Workout(
                            year = workout.year,
                            month = workout.month,
                            date = workout.date,
                            breakfastImg = workout.breakfastImg,
                            lunchImg = workout.lunchImg,
                            dinnerImg = workout.dinnerImg,
                            workoutType = null,
                            workoutTime = null,
                            workoutImg = null,
                        )
                        filteredList.add(filteredWorkout)
                    }
                }
                newGalleryAdapter = NewGalleryAdapter(filteredList)
                GalleryRecyclerView.adapter = newGalleryAdapter
                GalleryRecyclerView.layoutManager = LinearLayoutManager(context)
            }

            ExerciseBtn.setOnClickListener{
                val filteredList2 = mutableListOf<Workout>()
                TitleView.setText("Workout Gallery")
                for (workout in WorkoutList) {
                    if(workout.workoutImg != null) {
                        val filteredWorkout = Workout(
                            year = workout.year,
                            month = workout.month,
                            date = workout.date,
                            breakfastImg = null,
                            lunchImg = null,
                            dinnerImg = null,
                            workoutType = workout.workoutType,
                            workoutTime = workout.workoutTime,
                            workoutImg = workout.workoutImg,
                        )
                        filteredList2.add(filteredWorkout)
                    }
                }
                newGalleryAdapter = NewGalleryAdapter(filteredList2)
                GalleryRecyclerView.adapter = newGalleryAdapter
                GalleryRecyclerView.layoutManager = LinearLayoutManager(context)
            }
        }

    }

    private fun navigateToAddContactFragment() {
        // Create an instance of the AddContact fragment
        val inputDataFragment = InputDataFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentNewGallery, inputDataFragment)
            .addToBackStack(null)
            .commit()
    }

}