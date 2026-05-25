package com.example.gymtrack.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Entity representing a workout template (routine).
 * Templates are reusable workout plans that users can create
 * and quickly start a workout session from the home screen.
 * exerciseCount tracks how many exercises are in the template.
 */
@Entity(tableName = "workout_templates")
data class WorkoutTemplateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val exerciseCount: Int = 0
)