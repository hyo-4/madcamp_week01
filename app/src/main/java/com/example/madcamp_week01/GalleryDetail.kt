package com.example.madcamp_week01

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.madcamp_week01.databinding.FragmentGalleryDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text


class GalleryDetail(detailImage : Uri, year:Int, month:Int, date:Int, type: String) : Fragment() {
    private lateinit var binding: FragmentGalleryDetailBinding
    lateinit var updateImg: ActivityResultLauncher<String>
    var db: AppDatabase? = null
    var newText = "${year} / ${month} / ${date}"
    var detailImg = detailImage
    var inputimage: Uri? = null
    val addYear = year
    val addMonth = month
    val addDay = date
    val newtype = type
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryDetailBinding.inflate(inflater,container,false)
        db = AppDatabase.getInstance(requireContext())


        Glide.with(requireContext()).load(detailImg).into(binding.detailGalleryImage)
        binding.detailContent.text = newtype
        binding.detailDate.text = newText

        binding.detailGalleryImage.setOnClickListener{
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        binding.editBtn.setOnClickListener{
            openImagePicker()
        }

        binding.saveBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                updateImage(inputimage, newtype)
                navigateBack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateBack()
        }

        updateImg =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
//                    requireActivity().contentResolver.takePersistableUriPermission(
//                        it,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    )
                    inputimage = it
                    binding.detailGalleryImage.setImageURI(it)
                }
            }
        return binding.root
    }

    private fun openImagePicker() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
        updateImg.launch("image/*")
    }


    private fun updateImage(img: Uri?, type: String) {
        val newWorkout = db?.workoutDao()?.getWorkoutByDate(addYear, addMonth, addDay)
        var updateWorkout: Workout? = newWorkout?.firstOrNull()
        val imgtype = type

//        if(img != null){
//            val newUpdate = Workout(
//                year = addYear,
//                month = addMonth,
//                date = addDay,
//                breakfastImg = img,
//                lunchImg = updateWorkout?.lunchImg,
//                dinnerImg = updateWorkout?.dinnerImg,
//                workoutImg = updateWorkout?.workoutImg,
//                workoutTime = updateWorkout?.workoutTime,
//                workoutType = updateWorkout?.workoutType
//            )
//            db?.workoutDao()?.insertOrUpdate(newUpdate)
//            Log.d("newUpdate", newUpdate.toString())
//        }

        if (img != null) {
            val newUpdate = when (type) {
                "breakfast" -> {
                    updateWorkout?.breakfastImg = img
                    updateWorkout
                }
                "lunch" -> {
                    updateWorkout?.lunchImg = img
                    updateWorkout
                }
                "dinner" -> {
                    updateWorkout?.dinnerImg = img
                    updateWorkout
                }
                "workout" -> {
                    updateWorkout?.workoutImg = img
                    updateWorkout?.workoutTime = updateWorkout?.workoutTime
                    updateWorkout?.workoutType = updateWorkout?.workoutType
                    updateWorkout
                }
                else -> updateWorkout
            }

            newUpdate?.let {
                db?.workoutDao()?.insertOrUpdate(it)
                Log.d("newUpdate", it.toString())
            }
        }


    }

    private fun navigateBack() {
        val myFragment = NewGallery()

        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.galleryDetailContainer, myFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}