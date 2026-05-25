package com.example.gymtrack

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
/**
 * Worker class for scheduling daily workout reminders.
 * Executed by WorkManager once per day in the background.
 * Shows a reminder notification even when the app is closed.
 */
class ReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        NotificationHelper.showReminderNotification(applicationContext)
        return Result.success()
    }
}