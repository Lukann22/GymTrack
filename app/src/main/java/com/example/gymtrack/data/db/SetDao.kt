package com.example.gymtrack.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
/**
 * Data Access Object for set operations within an exercise.
 * Sets are the most granular data unit in GymTrack - each set
 * stores weight and reps for a specific exercise in a workout.
 */
@Dao
interface SetDao {

    @Insert
    suspend fun insertSet(set: SetEntity): Long

    @Query("SELECT * FROM sets WHERE exerciseId = :exerciseId")
    fun getSetsForExercise(exerciseId: Long): LiveData<List<SetEntity>>

    @Delete
    suspend fun deleteSet(set: SetEntity)
}