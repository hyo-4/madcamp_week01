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
import androidx.appcompat.content.res.AppCompatResources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class Free : Fragment() {
    lateinit var calendarV : CalendarView
    lateinit var todayDietV : TextView
    lateinit var todayWorkoutV : TextView
    lateinit var bfImgV : ImageButton
    lateinit var lunchImgV : ImageButton
    lateinit var dinnerImgV : ImageButton
    lateinit var wImgV : ImageButton
    lateinit var wTypeV : TextView
    lateinit var wTimeV : TextView
    lateinit var worktypeV : TextView
    lateinit var worktimeV : TextView
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
        todayDietV = view.findViewById(R.id.diet)
        todayWorkoutV = view.findViewById(R.id.todayworkout)
        bfImgV = view.findViewById(R.id.breakfast)
        lunchImgV = view.findViewById(R.id.lunch)
        dinnerImgV = view.findViewById(R.id.dinner)
        wImgV = view.findViewById(R.id.workoutimg)
        wTypeV = view.findViewById(R.id.wtype)
        wTimeV = view.findViewById(R.id.wtime)
        worktypeV = view.findViewById(R.id.workoutType)
        worktimeV = view.findViewById(R.id.workoutTime)
        db = AppDatabase.getInstance(requireContext())

        val todayCalendar = Calendar.getInstance()
        val todayYear = todayCalendar.get(Calendar.YEAR)
        val todayMonth = todayCalendar.get(Calendar.MONTH) + 1
        val todayDay = todayCalendar.get(Calendar.DAY_OF_MONTH)

        val noWOimg = AppCompatResources.getDrawable(requireContext(), R.drawable.noworkout)
        val noBFimg = AppCompatResources.getDrawable(requireContext(), R.drawable.nobreakfast)
        val noLimg = AppCompatResources.getDrawable(requireContext(), R.drawable.nolunch)
        val noDimg = AppCompatResources.getDrawable(requireContext(), R.drawable.nodinner)

        bfImgV.setOnClickListener {
            navigateToNewBreakFast(todayYear, todayMonth, todayDay)
        }




        calendarV.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-$month-$dayOfMonth"
            Log.d("date", "$selectedDate")
            CoroutineScope(Dispatchers.IO).launch{
                val dateWorkout = db?.workoutDao()?.getWorkoutByDate(year, month+1, dayOfMonth)
                var selectedWorkout: Workout? = dateWorkout?.firstOrNull()  //선택한 날짜의 workout 데이터

                withContext(Dispatchers.Main) {
                    if (selectedWorkout != null) {
                        // changing View to uploaded images
                        // **changing 운동 이미지
                        if (selectedWorkout.workoutImg != null) {
                            wImgV.setImageURI(selectedWorkout.workoutImg)
                            worktypeV.text = selectedWorkout.workoutType
                            worktimeV.text = selectedWorkout.workoutTime
                        } else {
                            wImgV.setImageDrawable(noWOimg)
                            worktypeV.text = resources.getString(R.string.noexercise)
                            worktimeV.text = resources.getString(R.string.noexercise)
                        }
                        // **changing 아침 식단 이미지
                        if (selectedWorkout.breakfastImg != null) {
                            bfImgV.setImageURI(selectedWorkout.breakfastImg)
                        } else {
                            bfImgV.setImageDrawable(noBFimg)
                        }
                        // **changing 점심 식단 이미지
                        if (selectedWorkout.lunchImg != null) {
                            lunchImgV.setImageURI(selectedWorkout.lunchImg)
                        } else {
                            lunchImgV.setImageDrawable(noLimg)
                        }
                        // **changing 저녁 식단 이미지
                        if (selectedWorkout.dinnerImg != null) {
                            dinnerImgV.setImageURI(selectedWorkout.dinnerImg)
                        } else {
                            dinnerImgV.setImageDrawable(noDimg)
                        }
                        // changing View finished
                    } else {    // 선택된 날짜에 해당하는 데이터가 없는 경우
                        // db에 해당 날짜의 데이터를 추가
//                        val newWorkout =
//                            Workout(year, month + 1, dayOfMonth, null, null, null, null, null, null)
//                        db?.workoutDao()?.insertAll(newWorkout)
//                        wImgV.setImageDrawable(noWOimg)
//                        bfImgV.setImageDrawable(noBFimg)
//                        lunchImgV.setImageDrawable(noLimg)
//                        dinnerImgV.setImageDrawable(noDimg)
                    }
                }
            }
            bfImgV.setOnClickListener {
                navigateToNewBreakFast(year, month+1, dayOfMonth) // Workout data 전체 보내기
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