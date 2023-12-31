package com.example.madcamp_week01


import android.net.Uri
import android.util.Log
import androidx.room.*

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

    @Update
    fun update(workout: Workout)

    @Transaction
    fun insertOrUpdate(workout: Workout) {
        val existingWorkout = getWorkoutByDate(workout.year, workout.month, workout.date)
        var check: Workout? = existingWorkout?.firstOrNull()
        if (check != null) {
            // Workout data already exists, update it
            update(workout)
        } else {
            // Workout data does not exist, insert a new one
            insertAll(workout)
        }
    }
}