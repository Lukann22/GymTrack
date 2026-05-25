package com.example.gymtrack.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
/**
 * Data Access Object for exercise operations within a workout.
 * Provides filtered queries - exercises are always fetched per workout,
 * never all at once, to keep memory usage low during active sessions.
 */
@Dao
interface ExerciseDao {

    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity): Long

    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId")
    fun getExercisesForWorkout(workoutId: Long): LiveData<List<ExerciseEntity>>

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)
}