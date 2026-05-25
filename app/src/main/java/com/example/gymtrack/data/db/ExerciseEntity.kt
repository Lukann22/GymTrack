package com.example.gymtrack.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Entity representing an exercise within a workout session.
 * Each exercise belongs to a specific workout via workoutId foreign key.
 * Cascade delete ensures exercises are removed when workout is deleted.
 */
@Entity(
    tableName = "exercises",
    foreignKeys = [ForeignKey(
        entity = WorkoutEntity::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val workoutId: Long
)