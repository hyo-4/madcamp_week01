package com.example.madcamp_week01

import android.app.Dialog
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide

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

            workout.workoutImg?.let {
                Glide.with(itemView.context).load(it).into(workoutImageView)
                workoutImageView.visibility = View.VISIBLE
                workoutView.visibility = View.VISIBLE
                detailTextView.visibility= View.VISIBLE
                detailTextView.text = "${workout.workoutTime} 동안 ${workout.workoutType} 했어요"
            } ?: run {
                workoutImageView.visibility = View.GONE
                workoutView.visibility = View.GONE
                detailTextView.visibility = View.GONE
            }

            // Load and display breakfast image if it exists
            workout.breakfastImg?.let {
                Glide.with(itemView.context).load(it).into(breakfastImageView)
                breakfastImageView.visibility = View.VISIBLE
                breakfastView.visibility = View.VISIBLE
            } ?: run {
                breakfastImageView.visibility = View.GONE
                breakfastView.visibility = View.GONE
            }

            // Load and display lunch image if it exists
            workout.lunchImg?.let {
                Glide.with(itemView.context).load(it).into(lunchImageView)
                lunchImageView.visibility = View.VISIBLE
                lunchView.visibility= View.VISIBLE
            } ?: run {
                lunchImageView.visibility = View.GONE
                lunchView.visibility= View.GONE
            }

            // Load and display dinner image if it exists
            workout.dinnerImg?.let {
                Glide.with(itemView.context).load(it).into(dinnerImageView)
                dinnerImageView.visibility = View.VISIBLE
                dinnerView.visibility = View.VISIBLE
            } ?: run {
                dinnerImageView.visibility = View.GONE
                dinnerView.visibility = View.GONE
            }

            dateView.text = "${workout.year}/${workout.month}/${workout.date}"

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