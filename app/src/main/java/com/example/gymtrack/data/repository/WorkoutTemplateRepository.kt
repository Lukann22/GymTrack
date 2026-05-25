package com.example.gymtrack.data.repository

import androidx.lifecycle.LiveData
import com.example.gymtrack.data.db.WorkoutTemplateDao
import com.example.gymtrack.data.db.WorkoutTemplateEntity
/**
 * Repository for workout template operations.
 * Handles CRUD operations for user-created workout templates.
 */
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