package com.example.gymtrack.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
/**
 * Data Access Object for workout template operations.
 * Templates are user-created workout routines shown on the home screen.
 * Ordered alphabetically for easy navigation.
 */
@Dao
interface WorkoutTemplateDao {

    @Insert
    suspend fun insertTemplate(template: WorkoutTemplateEntity): Long

    @Query("SELECT * FROM workout_templates ORDER BY name ASC")
    fun getAllTemplates(): LiveData<List<WorkoutTemplateEntity>>

    @Delete
    suspend fun deleteTemplate(template: WorkoutTemplateEntity)
}