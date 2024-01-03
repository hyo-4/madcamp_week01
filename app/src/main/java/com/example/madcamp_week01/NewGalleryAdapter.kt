package com.example.madcamp_week01

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class NewGalleryAdapter(var WorkoutList: List<Workout>?) : RecyclerView.Adapter<NewGalleryAdapter.ContactViewHolder>() {

    lateinit var galleryContext: Context

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var index: Int? = null
        val workoutImageView: ImageView = itemView.findViewById(R.id.workoutView)
        val breakfastImageView: ImageView = itemView.findViewById(R.id.breakfastView)
        val lunchImageView: ImageView = itemView.findViewById(R.id.lunchView)
        val dinnerImageView: ImageView = itemView.findViewById(R.id.dinnerView)
        val dateView: TextView = itemView.findViewById(R.id.dateView)
        val breakfastView: TextView = itemView.findViewById(R.id.breakfastText)
        val lunchView: TextView = itemView.findViewById(R.id.lunchText)
        val dinnerView: TextView = itemView.findViewById(R.id.dinnerText)
        val workoutView: TextView = itemView.findViewById(R.id.WorkoutText)
        val detailTextView : TextView = itemView.findViewById(R.id.WorkoutDetailText)

        fun bind(workout: Workout, position: Int) {
            index = position
            Log.d("workout",workout.toString())
            val transformation = MultiTransformation(
                CenterCrop(),
                RoundedCorners(50)
            )

            workout.workoutImg?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .apply(RequestOptions.bitmapTransform(transformation))
                    .into(workoutImageView)
                workoutImageView.visibility = View.VISIBLE
                workoutView.visibility = View.VISIBLE
                detailTextView.visibility= View.VISIBLE
                detailTextView.text = "${workout.workoutTime} 동안 ${workout.workoutType} 했어요"
                workoutImageView.setOnClickListener {
                   showDetailFragment(workout.workoutImg!!,workout.year,workout.month,workout.date,"workout")
                }

            } ?: run {
                workoutImageView.visibility = View.GONE
                workoutView.visibility = View.GONE
                detailTextView.visibility = View.GONE
            }

            // Load and display breakfast image if it exists
            workout.breakfastImg?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .apply(RequestOptions.bitmapTransform(transformation))
                    .into(breakfastImageView)
                breakfastImageView.visibility = View.VISIBLE
                breakfastView.visibility = View.VISIBLE
                breakfastImageView.setOnClickListener {
                    showDetailFragment(workout.breakfastImg!!,workout.year,workout.month,workout.date,"breakfast")
                }
            } ?: run {
                breakfastImageView.visibility = View.GONE
                breakfastView.visibility = View.GONE
            }

            // Load and display lunch image if it exists
            workout.lunchImg?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .apply(RequestOptions.bitmapTransform(transformation))
                    .into(lunchImageView)
                lunchImageView.visibility = View.VISIBLE
                lunchView.visibility= View.VISIBLE
                lunchImageView.setOnClickListener {
                    showDetailFragment(workout.lunchImg!!,workout.year,workout.month,workout.date,"lunch")
                }
            } ?: run {
                lunchImageView.visibility = View.GONE
                lunchView.visibility= View.GONE
            }

            // Load and display dinner image if it exists
            workout.dinnerImg?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .apply(RequestOptions.bitmapTransform(transformation))
                    .into(dinnerImageView)
                dinnerImageView.visibility = View.VISIBLE
                dinnerView.visibility = View.VISIBLE
                dinnerImageView.setOnClickListener {
                    showDetailFragment(workout.dinnerImg!!,workout.year,workout.month,workout.date,"dinner")
                }
            } ?: run {
                dinnerImageView.visibility = View.GONE
                dinnerView.visibility = View.GONE
            }

            dateView.text = "${workout.year}/${workout.month}/${workout.date}"

        }

        private fun showDetailFragment(image:Uri, year:Int, month:Int, date:Int, type: String) {
            val detailFragment = GalleryDetail(image,year,month,date,type)

            val fragmentTransaction = (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentNewGallery, detailFragment)
            fragmentTransaction.addToBackStack(null) // Optional: Adds the transaction to the back stack
            fragmentTransaction.commit()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        galleryContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val reversedPosition = WorkoutList!!.size - position - 1
       holder.bind(WorkoutList!![reversedPosition], position)
    }

    override fun getItemCount(): Int {
        return WorkoutList?.size ?: 0
    }


}