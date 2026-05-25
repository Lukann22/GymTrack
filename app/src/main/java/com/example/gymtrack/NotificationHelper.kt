package com.example.gymtrack

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
/**
 * Helper object for managing notifications in GymTrack.
 * Handles three types of notifications:
 * - Workout complete: shown when user finishes a workout session
 * - Daily reminder: shown when user hasn't worked out today
 * - Personal record: shown when user achieves a new weight record
 */
object NotificationHelper {

    private const val CHANNEL_ID = "gymtrack_channel"
    private const val CHANNEL_NAME = "GymTrack Notifications"

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun showWorkoutCompleteNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Workout Complete! 💪")
            .setContentText("Great job! Your workout has been saved.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(1, notification)
    }


    fun showReminderNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Time to train! 🏋️")
            .setContentText("You haven't worked out today. Let's go!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(2, notification)
    }

    fun showPersonalRecordNotification(context: Context, exerciseName: String, weight: Float) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("New Personal Record! 🎉")
            .setContentText("$exerciseName: ${weight}kg - Your best yet!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(3, notification)
    }
}
