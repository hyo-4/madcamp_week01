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
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.madcamp_week01.databinding.AddcontactBinding
import com.example.madcamp_week01.databinding.WorkoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Free : Fragment() {
    private lateinit var binding: WorkoutBinding
    var db: AppDatabase? = null
    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = WorkoutBinding.inflate(inflater, container, false)
        val bottomSheetLayout: LinearLayout = binding.layout.bottomSheetLayout
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())

        val todayCalendar = Calendar.getInstance()
        val todayYear = todayCalendar.get(Calendar.YEAR)
        val todayMonth = todayCalendar.get(Calendar.MONTH) + 1
        val todayDay = todayCalendar.get(Calendar.DAY_OF_MONTH)
        showView(todayYear, todayMonth, todayDay)

        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val today = dateFormat.format(todayCalendar.time)
        binding.layout.todaydate.text = today

        binding.layout.breakfast.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            navigateToNewBreakFast(todayYear, todayMonth, todayDay)
        }
        binding.layout.lunch.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            navigateToNewLunch(todayYear, todayMonth, todayDay)
        }
        binding.layout.dinner.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            navigateToNewDinner(todayYear, todayMonth, todayDay)
        }
        binding.layout.workoutimg.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            navigateToNewWorkout(todayYear, todayMonth, todayDay)
        }


        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            showView(year, month+1, dayOfMonth)
            binding.layout.breakfast.setOnClickListener {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                navigateToNewBreakFast(year, month+1, dayOfMonth) // Workout data 전체 보내기
            }
            binding.layout.lunch.setOnClickListener {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                navigateToNewLunch(year, month+1, dayOfMonth) // Workout data 전체 보내기
            }
            binding.layout.dinner.setOnClickListener {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                navigateToNewDinner(year, month+1, dayOfMonth) // Workout data 전체 보내기
            }
            binding.layout.workoutimg.setOnClickListener {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                navigateToNewWorkout(year, month+1, dayOfMonth) // Workout data 전체 보내기
            }

            val monthFormatted = String.format(Locale.getDefault(), "%02d", month+1)
            val dayFormatted = String.format(Locale.getDefault(), "%02d", dayOfMonth)
            binding.layout.todaydate.text = "$year/$monthFormatted/$dayFormatted"
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun showView(year:Int, month:Int, day:Int){
        CoroutineScope(Dispatchers.IO).launch{
            val dateWorkout = db?.workoutDao()?.getWorkoutByDate(year, month, day)
            var selectedWorkout: Workout? = dateWorkout?.firstOrNull()  //선택한 날짜의 workout 데이터
            Log.d("check", "$selectedWorkout")
            val transformation = MultiTransformation(
                CenterCrop(),
                RoundedCorners(50)
            )

            withContext(Dispatchers.Main) {
                if (selectedWorkout != null) {
                    // changing View to uploaded images
                    // **changing 운동 이미지
                    if (selectedWorkout.workoutImg != null) {
                        Glide.with(requireContext())
                            .load(selectedWorkout.workoutImg)
                            .apply(RequestOptions.bitmapTransform(transformation))
                            .into(binding.layout.workoutimg)
                        binding.layout.workoutType.text = selectedWorkout.workoutType
                        binding.layout.workoutTime.text = selectedWorkout.workoutTime
                    } else {
                        Glide.with(requireContext())
                            .load(R.drawable.blankimg)
                            .apply(RequestOptions.bitmapTransform(transformation))
                            .into(binding.layout.workoutimg)
                        binding.layout.workoutType.text = resources.getString(R.string.noexercise)
                        binding.layout.workoutTime.text = resources.getString(R.string.noexercise)
                    }
                    // **changing 아침 식단 이미지
                    if (selectedWorkout.breakfastImg != null) {
                        Glide.with(requireContext())
                            .load(selectedWorkout.breakfastImg)
                            .apply(RequestOptions.bitmapTransform(transformation))
                            .into(binding.layout.breakfast)
                    } else {
                        Glide.with(requireContext())
                            .load(R.drawable.blankimg)
                            .apply(RequestOptions.bitmapTransform(transformation))
                            .into(binding.layout.breakfast)
                    }
                    // **changing 점심 식단 이미지
                    if (selectedWorkout.lunchImg != null) {
                        Glide.with(requireContext())
                            .load(selectedWorkout.lunchImg)
                            .apply(RequestOptions.bitmapTransform(transformation))
                            .into(binding.layout.lunch)
                    } else {
                        Glide.with(requireContext())
                            .load(R.drawable.blankimg)
                            .apply(RequestOptions.bitmapTransform(transformation))
                            .into(binding.layout.lunch)
                    }
                    // **changing 저녁 식단 이미지
                    if (selectedWorkout.dinnerImg != null) {
                        Glide.with(requireContext())
                            .load(selectedWorkout.dinnerImg)
                            .apply(RequestOptions.bitmapTransform(transformation))
                            .into(binding.layout.dinner)
                    } else {
                        Glide.with(requireContext())
                            .load(R.drawable.blankimg)
                            .apply(RequestOptions.bitmapTransform(transformation))
                            .into(binding.layout.dinner)
                    }
                    // changing View finished
                } else {    // 선택된 날짜에 해당하는 데이터가 없는 경우
                    // db에 해당 날짜의 데이터를 추가
                    Glide.with(requireContext())
                        .load(R.drawable.blankimg)
                        .apply(RequestOptions.bitmapTransform(transformation))
                        .into(binding.layout.workoutimg)
                    Glide.with(requireContext())
                        .load(R.drawable.blankimg)
                        .apply(RequestOptions.bitmapTransform(transformation))
                        .into(binding.layout.breakfast)
                    Glide.with(requireContext())
                        .load(R.drawable.blankimg)
                        .apply(RequestOptions.bitmapTransform(transformation))
                        .into(binding.layout.lunch)
                    Glide.with(requireContext())
                        .load(R.drawable.blankimg)
                        .apply(RequestOptions.bitmapTransform(transformation))
                        .into(binding.layout.dinner)
                    binding.layout.workoutType.text = resources.getString(R.string.noexercise)
                    binding.layout.workoutTime.text = resources.getString(R.string.noexercise)
                }
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
    private fun navigateToNewLunch(year:Int, month: Int, day: Int){
        val newlunch = NewLunch(year, month, day)
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.calendarFragment, newlunch)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
    private fun navigateToNewDinner(year:Int, month: Int, day: Int){
        val newdinner = NewDinnner(year, month, day)
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.calendarFragment, newdinner)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
    private fun navigateToNewWorkout(year:Int, month: Int, day: Int){
        val newworkout = NewWorkout(year, month, day)
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.calendarFragment, newworkout)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

}