package com.example.madcamp_week01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.TextView
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Free : Fragment() {
    lateinit var calendarV : CalendarView
    lateinit var todayDiet : TextView
    lateinit var todayWorkout : TextView
    lateinit var bfImg : ImageButton
    lateinit var lunchImg : ImageButton
    lateinit var dinnerImg : ImageButton
    lateinit var wImg : ImageButton
    lateinit var wType : TextView
    lateinit var wTime : TextView
    lateinit var worktype : TextView
    lateinit var worktime : TextView
    var db: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_free, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarV = view.findViewById(R.id.calendar)
        todayDiet = view.findViewById(R.id.diet)
        todayWorkout = view.findViewById(R.id.todayworkout)
        bfImg = view.findViewById(R.id.breakfast)
        lunchImg = view.findViewById(R.id.lunch)
        dinnerImg = view.findViewById(R.id.dinner)
        wImg = view.findViewById(R.id.workoutimg)
        wType = view.findViewById(R.id.wtype)
        wTime = view.findViewById(R.id.wtime)
        worktype = view.findViewById(R.id.workoutType)
        worktime = view.findViewById(R.id.workoutTime)
        db = AppDatabase.getInstance(requireContext())


        calendarV.setOnDateChangeListener { _, year, month, dayOfMonth ->
//            val selectedDate = "$year-$month-$dayOfMonth"
//            Log.d("date", "$selectedDate")
//            CoroutineScope(Dispatchers.IO).launch{
//                val dateWorkout = db?.workoutDao()?.getWorkoutByDate(year, month, dayOfMonth)
//                if (dateWorkout != null) {
//
//                } else {
//
//                }
//            }
            bfImg.setOnClickListener {
                navigateToNewBreakFast(year, month+1, dayOfMonth)
            }


        }

    }
    private fun navigateToNewBreakFast(year:Int, month: Int, day: Int){
        val newbreakfast = NewBreakFast(year, month, day)
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.calendarFragment, newbreakfast)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
}