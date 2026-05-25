package com.example.gymtrack.data.repository

import androidx.lifecycle.LiveData
import com.example.gymtrack.data.db.ExerciseLibraryDao
import com.example.gymtrack.data.db.ExerciseLibraryEntity
/**
 * Repository for exercise library operations.
 * Provides access to the global exercise library stored in Room database.
 */
class ExerciseLibraryRepository(
    private val exerciseLibraryDao: ExerciseLibraryDao
) {

    val allExercises: LiveData<List<ExerciseLibraryEntity>> =
        exerciseLibraryDao.getAllExercises()

    fun getExercisesByMuscleGroup(muscleGroup: String): LiveData<List<ExerciseLibraryEntity>> {
        return exerciseLibraryDao.getExercisesByMuscleGroup(muscleGroup)
    }

    suspend fun insertExercise(exercise: ExerciseLibraryEntity): Long {
        return exerciseLibraryDao.insertExercise(exercise)
    }

    suspend fun deleteExercise(exercise: ExerciseLibraryEntity) {
        exerciseLibraryDao.deleteExercise(exercise)
    }
}