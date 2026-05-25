package com.example.gymtrack.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insertWorkout(workout: WorkoutEntity): Long

    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): LiveData<List<WorkoutEntity>>

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)

    @Query("SELECT SUM(s.weight * s.reps) FROM sets s INNER JOIN exercises e ON s.exerciseId = e.id WHERE e.workoutId = :workoutId")
    suspend fun getTotalVolume(workoutId: Long): Float?
}