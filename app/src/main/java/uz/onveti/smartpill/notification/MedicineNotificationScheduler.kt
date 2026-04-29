package uz.onveti.smartpill.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import uz.onveti.smartpill.data.medicine.Medicine
import java.util.Calendar
import kotlin.math.abs

class MedicineNotificationScheduler(
    private val context: Context,
) {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(medicine: Medicine) {
        listOf(
            medicine.morningTime,
            medicine.afternoonTime,
            medicine.eveningTime,
        ).forEachIndexed { index, time ->
            val triggerAtMillis = nextTriggerAtMillis(time) ?: return@forEachIndexed
            val notificationId = abs("${medicine.id}-$index".hashCode())

            scheduleAlarm(
                medicineName = medicine.name,
                notificationId = notificationId,
                requestCode = notificationId,
                triggerAtMillis = triggerAtMillis,
                reminderType = MedicineNotificationReceiver.REMINDER_TYPE_PRIMARY,
            )

            scheduleAlarm(
                medicineName = medicine.name,
                notificationId = notificationId + FOLLOW_UP_ID_OFFSET,
                requestCode = notificationId + FOLLOW_UP_ID_OFFSET,
                triggerAtMillis = triggerAtMillis + FOLLOW_UP_DELAY_MILLIS,
                reminderType = MedicineNotificationReceiver.REMINDER_TYPE_FOLLOW_UP,
            )
        }
    }

    fun cancel(medicine: Medicine) {
        listOf(
            medicine.morningTime,
            medicine.afternoonTime,
            medicine.eveningTime,
        ).forEachIndexed { index, time ->
            if (time.isBlank()) return@forEachIndexed

            val notificationId = abs("${medicine.id}-$index".hashCode())
            cancelAlarm(notificationId)
            cancelAlarm(notificationId + FOLLOW_UP_ID_OFFSET)
        }
    }

    private fun scheduleAlarm(
        medicineName: String,
        notificationId: Int,
        requestCode: Int,
        triggerAtMillis: Long,
        reminderType: String,
    ) {
        val intent = Intent(context, MedicineNotificationReceiver::class.java).apply {
            putExtra(MedicineNotificationReceiver.EXTRA_MEDICINE_NAME, medicineName)
            putExtra(MedicineNotificationReceiver.EXTRA_NOTIFICATION_ID, notificationId)
            putExtra(MedicineNotificationReceiver.EXTRA_REMINDER_TYPE, reminderType)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            } else {
                // Fallback to inexact if permission not granted
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        }
    }

    private fun cancelAlarm(requestCode: Int) {
        val intent = Intent(context, MedicineNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun nextTriggerAtMillis(time: String): Long? {
        val parts = time.split(":")
        if (parts.size != 2) return null

        val hour = parts[0].toIntOrNull() ?: return null
        val minute = parts[1].toIntOrNull() ?: return null
        if (hour !in 0..23 || minute !in 0..59) return null

        val now = Calendar.getInstance()
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= now.timeInMillis) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }.timeInMillis
    }

    private companion object {
        const val FOLLOW_UP_DELAY_MILLIS = 60 * 60 * 1000L
        const val FOLLOW_UP_ID_OFFSET = 100_000
    }
}
