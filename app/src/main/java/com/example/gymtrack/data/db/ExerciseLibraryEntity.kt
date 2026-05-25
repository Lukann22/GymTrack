package com.example.gymtrack.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Entity representing an exercise in the global exercise library.
 * Unlike ExerciseEntity, this is not tied to a specific workout.
 * Contains exercise name and muscle group for categorization.
 */
@Entity(tableName = "exercise_library")
data class ExerciseLibraryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val muscleGroup: String
)