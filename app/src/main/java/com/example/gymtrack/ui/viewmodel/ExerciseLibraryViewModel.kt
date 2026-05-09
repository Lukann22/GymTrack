package com.example.gymtrack.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gymtrack.data.db.ExerciseLibraryEntity
import com.example.gymtrack.data.db.GymTrackDatabase
import com.example.gymtrack.data.repository.ExerciseLibraryRepository
import kotlinx.coroutines.launch

class ExerciseLibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExerciseLibraryRepository
    val allExercises: LiveData<List<ExerciseLibraryEntity>>

    init {
        val db = GymTrackDatabase.getDatabase(application)
        repository = ExerciseLibraryRepository(db.exerciseLibraryDao())
        allExercises = repository.allExercises
    }

    fun getExercisesByMuscleGroup(muscleGroup: String): LiveData<List<ExerciseLibraryEntity>> {
        return repository.getExercisesByMuscleGroup(muscleGroup)
    }

    fun insertExercise(exercise: ExerciseLibraryEntity) = viewModelScope.launch {
        repository.insertExercise(exercise)
    }

    fun deleteExercise(exercise: ExerciseLibraryEntity) = viewModelScope.launch {
        repository.deleteExercise(exercise)
    }
}