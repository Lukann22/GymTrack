package com.example.gymtrack.data.repository

import androidx.lifecycle.LiveData
import com.example.gymtrack.data.db.ExerciseDao
import com.example.gymtrack.data.db.ExerciseEntity
import com.example.gymtrack.data.db.WorkoutDao
import com.example.gymtrack.data.db.WorkoutEntity
import com.example.gymtrack.data.db.SetDao
import com.example.gymtrack.data.db.SetEntity
/**
 * Repository for workout data operations.
 * Acts as a single source of truth between ViewModel and Room database.
 * Handles WorkoutEntity, ExerciseEntity and SetEntity operations.
 */
class WorkoutRepository(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao,
    private val setDao: SetDao
) {

    val allWorkouts: LiveData<List<WorkoutEntity>> = workoutDao.getAllWorkouts()

    suspend fun insertWorkout(workout: WorkoutEntity): Long {
        return workoutDao.insertWorkout(workout)
    }

    suspend fun deleteWorkout(workout: WorkoutEntity) {
        workoutDao.deleteWorkout(workout)
    }

    fun getExercisesForWorkout(workoutId: Long): LiveData<List<ExerciseEntity>> {
        return exerciseDao.getExercisesForWorkout(workoutId)
    }

    suspend fun insertExercise(exercise: ExerciseEntity): Long {
        return exerciseDao.insertExercise(exercise)
    }

    suspend fun deleteExercise(exercise: ExerciseEntity) {
        exerciseDao.deleteExercise(exercise)
    }

    fun getSetsForExercise(exerciseId: Long): LiveData<List<SetEntity>> {
        return setDao.getSetsForExercise(exerciseId)
    }

    suspend fun insertSet(set: SetEntity): Long {
        return setDao.insertSet(set)
    }

    suspend fun deleteSet(set: SetEntity) {
        setDao.deleteSet(set)
    }

    suspend fun getTotalVolume(workoutId: Long): Float? {
        return workoutDao.getTotalVolume(workoutId)
    }
}