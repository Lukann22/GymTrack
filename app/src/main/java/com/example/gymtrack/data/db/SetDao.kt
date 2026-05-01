package com.example.gymtrack.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SetDao {

    @Insert
    suspend fun insertSet(set: SetEntity): Long

    @Query("SELECT * FROM sets WHERE exerciseId = :exerciseId")
    fun getSetsForExercise(exerciseId: Long): LiveData<List<SetEntity>>

    @Delete
    suspend fun deleteSet(set: SetEntity)
}