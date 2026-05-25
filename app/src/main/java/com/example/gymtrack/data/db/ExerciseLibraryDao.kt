package com.example.gymtrack.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
/**
 * Data Access Object for exercise library operations.
 * The exercise library is a global collection of exercises
 * independent of any specific workout session.
 * Supports filtering by muscle group for better organization.
 */
@Dao
interface ExerciseLibraryDao {

    @Insert
    suspend fun insertExercise(exercise: ExerciseLibraryEntity): Long

    @Query("SELECT * FROM exercise_library ORDER BY name ASC")
    fun getAllExercises(): LiveData<List<ExerciseLibraryEntity>>

    @Query("SELECT * FROM exercise_library WHERE muscleGroup = :muscleGroup")
    fun getExercisesByMuscleGroup(muscleGroup: String): LiveData<List<ExerciseLibraryEntity>>

    @Delete
    suspend fun deleteExercise(exercise: ExerciseLibraryEntity)
}