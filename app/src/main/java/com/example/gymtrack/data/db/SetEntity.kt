package com.example.gymtrack.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
/**
 * Entity representing a single set within an exercise.
 * Stores the weight in kilograms and number of repetitions.
 * Each set belongs to a specific exercise via exerciseId foreign key.
 * Cascade delete ensures sets are removed when exercise is deleted.
 */
@Entity(
    tableName = "sets",
    foreignKeys = [ForeignKey(
        entity = ExerciseEntity::class,
        parentColumns = ["id"],
        childColumns = ["exerciseId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val weight: Float,
    val reps: Int,
    val exerciseId: Long
)