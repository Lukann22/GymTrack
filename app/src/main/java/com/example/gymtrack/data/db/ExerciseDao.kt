package com.example.gymtrack.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity): Long

    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId")
    fun getExercisesForWorkout(workoutId: Long): LiveData<List<ExerciseEntity>>

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)
}