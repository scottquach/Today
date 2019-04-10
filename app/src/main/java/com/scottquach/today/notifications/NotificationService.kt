package com.scottquach.today.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.scottquach.today.MainActivity
import com.scottquach.today.R

class NotificationService(val context: Context) {

    init {
        createNotificationChannels()
    }

    companion object {
        const val ENTRY_REMINDER_CHANNEL_ID = "1"
        const val COMPLETED_REMINDER_CHANNEL_ID ="2"
    }

    /**
     * Show a notification with the passed in title, content, and id. On click the app will open.
     */
    fun showNotification(title: String, content: String, id: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notifBuilder = NotificationCompat.Builder(context,
            ENTRY_REMINDER_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(id, notifBuilder.build())
        }
    }

    /**
     * Creates all notifications channels used by the app
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val entryChannel = NotificationChannel(
                ENTRY_REMINDER_CHANNEL_ID, context.getString(
                    R.string.entry_reminder_channel_name
                ), importance).apply {
                this.description = context.getString(R.string.entry_reminder_channel_description)
            }

            val completedChannel = NotificationChannel(
                COMPLETED_REMINDER_CHANNEL_ID, context.getString(
                    R.string.completed_reminder_channel_name
                ), importance).apply {
                this.description = context.getString(R.string.completed_reminder_channel_description)
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(entryChannel)
            notificationManager.createNotificationChannel(completedChannel)
        }
    }
}