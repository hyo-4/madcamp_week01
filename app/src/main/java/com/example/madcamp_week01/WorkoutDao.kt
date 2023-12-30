package com.example.madcamp_week01


import android.net.Uri
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM tb_workout WHERE year = :year AND month = :month AND date = :date")
    fun getWorkoutByDate(year: Int, month: Int, date: Int): List<Workout>?

    @Query("SELECT * FROM tb_workout ORDER BY year, month, date ASC")
    fun getAll(): List<Workout>

    @Insert
    fun insertAll(vararg workout: Workout)

    @Delete
    fun delete(workout: Workout)
}