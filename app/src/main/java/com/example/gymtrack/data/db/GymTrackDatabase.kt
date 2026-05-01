package com.example.gymtrack.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        WorkoutEntity::class,
        ExerciseEntity::class,
        SetEntity::class,
        ExerciseLibraryEntity::class
    ],
    version = 1
)
abstract class GymTrackDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun setDao(): SetDao
    abstract fun exerciseLibraryDao(): ExerciseLibraryDao
    companion object {
        @Volatile
        private var INSTANCE: GymTrackDatabase? = null

        fun getDatabase(context: Context): GymTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GymTrackDatabase::class.java,
                    "gymtrack_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}