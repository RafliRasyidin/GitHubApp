package com.rasyidin.githubapp.core.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.ui.splash.SplashActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val ID_REMINDER = 100
        private const val CHANNEL_ID = "Channel_1"
        private const val CHANNEL_NAME = "Reminder Channel"
    }

    private lateinit var alarmManager: AlarmManager

    private lateinit var pendingIntent: PendingIntent

    private lateinit var intent: Intent

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        showNotification(
            context,
            context.resources.getString(R.string.notification_title),
            context.resources.getString(R.string.notification_message),
        )
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun showNotification(context: Context, title: String, message: String) {

        intent = Intent(context, SplashActivity::class.java)
        pendingIntent = PendingIntent.getActivity(
            context,
            ID_REMINDER,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(vibrationPattern)
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = vibrationPattern

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(ID_REMINDER, notification)
    }

    fun setReminder(context: Context) {

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        intent = Intent(context, AlarmReceiver::class.java)
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, R.string.reminder_message, Toast.LENGTH_SHORT).show()
    }

    fun cancelReminder(context: Context) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        intent = Intent(context, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, R.string.cancel_reminder_message, Toast.LENGTH_SHORT).show()
    }
}