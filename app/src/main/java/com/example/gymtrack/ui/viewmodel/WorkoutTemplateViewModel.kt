package com.example.gymtrack.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gymtrack.data.db.GymTrackDatabase
import com.example.gymtrack.data.db.WorkoutTemplateEntity
import com.example.gymtrack.data.repository.WorkoutTemplateRepository
import kotlinx.coroutines.launch

class WorkoutTemplateViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WorkoutTemplateRepository
    val allTemplates: LiveData<List<WorkoutTemplateEntity>>

    init {
        val db = GymTrackDatabase.getDatabase(application)
        repository = WorkoutTemplateRepository(db.workoutTemplateDao())
        allTemplates = repository.allTemplates
    }

    fun insertTemplate(template: WorkoutTemplateEntity) = viewModelScope.launch {
        repository.insertTemplate(template)
    }

    fun deleteTemplate(template: WorkoutTemplateEntity) = viewModelScope.launch {
        repository.deleteTemplate(template)
    }
}