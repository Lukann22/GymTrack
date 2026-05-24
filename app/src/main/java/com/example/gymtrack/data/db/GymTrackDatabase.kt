package com.example.gymtrack.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        WorkoutEntity::class,
        ExerciseEntity::class,
        SetEntity::class,
        ExerciseLibraryEntity::class ,
        WorkoutTemplateEntity::class
    ],
    version = 2
)

abstract class GymTrackDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun setDao(): SetDao
    abstract fun exerciseLibraryDao(): ExerciseLibraryDao

    abstract fun workoutTemplateDao(): WorkoutTemplateDao
    companion object {
        @Volatile
        private var INSTANCE: GymTrackDatabase? = null
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS workout_templates " +
                            "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "name TEXT NOT NULL, " +
                            "exerciseCount INTEGER NOT NULL DEFAULT 0)"
                )
            }
        }
        fun getDatabase(context: Context): GymTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GymTrackDatabase::class.java,
                    "gymtrack_database"
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
