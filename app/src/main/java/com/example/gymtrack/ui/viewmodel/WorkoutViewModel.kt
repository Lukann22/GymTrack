package com.example.gymtrack.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gymtrack.data.db.GymTrackDatabase
import com.example.gymtrack.data.db.ExerciseEntity
import com.example.gymtrack.data.db.SetEntity
import com.example.gymtrack.data.db.WorkoutEntity
import com.example.gymtrack.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WorkoutRepository
    val allWorkouts: LiveData<List<WorkoutEntity>>

    init {
        val db = GymTrackDatabase.getDatabase(application)
        repository = WorkoutRepository(
            db.workoutDao(),
            db.exerciseDao(),
            db.setDao()
        )
        allWorkouts = repository.allWorkouts
    }

    fun insertWorkout(workout: WorkoutEntity) = viewModelScope.launch {
        repository.insertWorkout(workout)
    }

    fun deleteWorkout(workout: WorkoutEntity) = viewModelScope.launch {
        repository.deleteWorkout(workout)
    }

    fun getExercisesForWorkout(workoutId: Long): LiveData<List<ExerciseEntity>> {
        return repository.getExercisesForWorkout(workoutId)
    }

    fun insertExercise(exercise: ExerciseEntity) = viewModelScope.launch {
        repository.insertExercise(exercise)
    }

    fun deleteExercise(exercise: ExerciseEntity) = viewModelScope.launch {
        repository.deleteExercise(exercise)
    }

    fun getSetsForExercise(exerciseId: Long): LiveData<List<SetEntity>> {
        return repository.getSetsForExercise(exerciseId)
    }

    fun insertSet(set: SetEntity) = viewModelScope.launch {
        repository.insertSet(set)
    }

    fun deleteSet(set: SetEntity) = viewModelScope.launch {
        repository.deleteSet(set)
    }
}