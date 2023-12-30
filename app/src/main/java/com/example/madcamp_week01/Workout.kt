package com.example.madcamp_week01

import android.net.Uri
import androidx.room.*

@Entity(tableName = "tb_workout", primaryKeys = ["year", "month", "date"])
data class Workout(
    var year: Int,
    var month: Int,
    var date: Int,
    var workoutImg: Uri?,
    var breakfastImg: Uri?,
    var lunchImg: Uri?,
    var dinnerImg: Uri?,
    var workoutType: String?,
    var workoutTime: String?,
)
