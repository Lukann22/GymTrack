package com.example.gymtrack.data.repository

import androidx.lifecycle.LiveData
import com.example.gymtrack.data.db.WorkoutTemplateDao
import com.example.gymtrack.data.db.WorkoutTemplateEntity

class WorkoutTemplateRepository(
    private val templateDao: WorkoutTemplateDao
) {
    val allTemplates: LiveData<List<WorkoutTemplateEntity>> = templateDao.getAllTemplates()

    suspend fun insertTemplate(template: WorkoutTemplateEntity): Long {
        return templateDao.insertTemplate(template)
    }

    suspend fun deleteTemplate(template: WorkoutTemplateEntity) {
        templateDao.deleteTemplate(template)
    }
}