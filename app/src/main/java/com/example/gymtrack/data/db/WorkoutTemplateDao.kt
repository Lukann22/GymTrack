package com.example.gymtrack.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WorkoutTemplateDao {

    @Insert
    suspend fun insertTemplate(template: WorkoutTemplateEntity): Long

    @Query("SELECT * FROM workout_templates ORDER BY name ASC")
    fun getAllTemplates(): LiveData<List<WorkoutTemplateEntity>>

    @Delete
    suspend fun deleteTemplate(template: WorkoutTemplateEntity)
}