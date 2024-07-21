package com.prafull.alarmy.alarms.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.prafull.alarmy.alarms.domain.AlarmItem
import com.prafull.alarmy.alarms.domain.RepeatMode
import java.time.DayOfWeek
import java.util.Calendar


class AlarmScheduler(private val context: Context) {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleAlarm(alarmItem: AlarmItem) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, alarmItem.fullHour)
            set(Calendar.MINUTE, alarmItem.minutes)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        when (alarmItem.repeatMode) {
            RepeatMode.ONCE -> scheduleOnceAlarm(alarmItem, calendar)
            RepeatMode.DAILY -> scheduleDailyAlarm(alarmItem, calendar)
            RepeatMode.MON_FRI -> scheduleMonFriAlarm(alarmItem, calendar)
            RepeatMode.CUSTOM -> scheduleCustomAlarm(alarmItem, calendar)
        }
    }

    private fun scheduleOnceAlarm(alarmItem: AlarmItem, calendar: Calendar) {
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        setExactAlarm(alarmItem, calendar.timeInMillis)
    }

    private fun scheduleDailyAlarm(alarmItem: AlarmItem, calendar: Calendar) {
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        setRepeatingAlarm(alarmItem, calendar.timeInMillis, AlarmManager.INTERVAL_DAY)
    }

    private fun scheduleMonFriAlarm(alarmItem: AlarmItem, calendar: Calendar) {
        while (true) {
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek in Calendar.MONDAY..Calendar.FRIDAY && calendar.timeInMillis > System.currentTimeMillis()) {
                break
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        setInexactRepeatingAlarm(alarmItem, calendar.timeInMillis, AlarmManager.INTERVAL_DAY)
    }

    private fun scheduleCustomAlarm(alarmItem: AlarmItem, calendar: Calendar) {
        while (true) {
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (alarmItem.customDays.contains(DayOfWeek.of(dayOfWeek)) && calendar.timeInMillis > System.currentTimeMillis()) {
                break
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        setInexactRepeatingAlarm(alarmItem, calendar.timeInMillis, AlarmManager.INTERVAL_DAY)
    }

    private fun setExactAlarm(alarmItem: AlarmItem, triggerAtMillis: Long) {
        val intent = createAlarmIntent(alarmItem)
        val pendingIntent = createPendingIntent(alarmItem, intent)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    private fun setRepeatingAlarm(
        alarmItem: AlarmItem,
        triggerAtMillis: Long,
        intervalMillis: Long
    ) {
        val intent = createAlarmIntent(alarmItem)
        val pendingIntent = createPendingIntent(alarmItem, intent)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis,
            pendingIntent
        )
    }

    private fun setInexactRepeatingAlarm(
        alarmItem: AlarmItem,
        triggerAtMillis: Long,
        intervalMillis: Long
    ) {
        val intent = createAlarmIntent(alarmItem)
        val pendingIntent = createPendingIntent(alarmItem, intent)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis,
            pendingIntent
        )
    }

    private fun createAlarmIntent(alarmItem: AlarmItem): Intent {
        return Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_UID", alarmItem.uid)
            putExtra("ALARM_MESSAGE", alarmItem.message)
            putExtra("REPEAT_MODE", alarmItem.repeatMode.name)
        }
    }

    private fun createPendingIntent(alarmItem: AlarmItem, intent: Intent): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            alarmItem.uid.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun cancelAlarm(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmItem.uid.hashCode(),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
    }
}