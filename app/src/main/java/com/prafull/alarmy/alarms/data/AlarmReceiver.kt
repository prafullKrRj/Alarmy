package com.prafull.alarmy.alarms.data

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.prafull.alarmy.alarms.ui.AlarmActivity
import com.prafull.alarmy.alarms.domain.RepeatMode
import java.util.Calendar


/**
 * Exact: The alarm will be delivered exactly at the specified time.
 * Inexact: The alarm will be delivered within a window of time.
 */
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alarmUid = intent.getStringExtra("ALARM_UID") ?: return
        val message = intent.getStringExtra("ALARM_MESSAGE") ?: "Alarm!"
        val repeatMode =
            intent.getStringExtra("REPEAT_MODE")?.let { RepeatMode.valueOf(it) } ?: RepeatMode.ONCE

        // Show notification and start AlarmActivity
        showNotification(context, alarmUid)
        startAlarmActivity(context, alarmUid, message)

        // Handle repeat modes
        when (repeatMode) {
            RepeatMode.ONCE -> disableAlarm(context, alarmUid)
            RepeatMode.DAILY -> {} // Do nothing, it's already set to repeat
            RepeatMode.MON_FRI -> handleMonFriAlarm(context, alarmUid)
            RepeatMode.CUSTOM -> handleCustomAlarm(context, alarmUid)
        }
    }

    private fun disableAlarm(context: Context, alarmUid: String) {
        // Disable the alarm in the database
        // You'll need to inject your database repository here
        // databaseRepository.updateAlarmStatus(alarmUid, false)
    }

    private fun handleMonFriAlarm(context: Context, alarmUid: String) {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        if (dayOfWeek == Calendar.FRIDAY) {
            // Reschedule for Monday
            calendar.add(Calendar.DAY_OF_YEAR, 3)
            rescheduleAlarm(context, alarmUid, calendar.timeInMillis)
        }
    }

    private fun handleCustomAlarm(context: Context, alarmUid: String) {
        // Reschedule for the next custom day
        // You'll need to fetch the alarm details from the database
        // val alarm = databaseRepository.getAlarmById(alarmUid)
        // alarm?.let {
        //     val nextAlarmTime = getNextCustomAlarmTime(it)
        //     rescheduleAlarm(context, alarmUid, nextAlarmTime)
        // }
    }

    private fun rescheduleAlarm(context: Context, alarmUid: String, triggerAtMillis: Long) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_UID", alarmUid)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmUid.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    private fun startAlarmActivity(context: Context, alarmUid: String, message: String) {
        val intent = Intent(context, AlarmActivity::class.java).apply {
            putExtra("ALARM_UID", alarmUid)
            putExtra("ALARM_MESSAGE", message)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    private fun showNotification(context: Context, alarmUid: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "alarm_channel",
            "Alarm Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Alarm")
            .setContentText("Wake up!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(alarmUid.hashCode(), notification)
    }
    // ... (keep the showNotification and startAlarmActivity methods from the previous example)
}