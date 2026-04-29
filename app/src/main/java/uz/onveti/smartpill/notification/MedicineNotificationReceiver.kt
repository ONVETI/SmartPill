package uz.onveti.smartpill.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import uz.onveti.smartpill.R
import uz.onveti.smartpill.MainActivity

class MedicineNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        createChannel(context)

        val medicineName = intent.getStringExtra(EXTRA_MEDICINE_NAME).orEmpty()
        val reminderType = intent.getStringExtra(EXTRA_REMINDER_TYPE).orEmpty()
        val title = if (reminderType == REMINDER_TYPE_FOLLOW_UP) {
            "Dori eslatmasi"
        } else {
            "Dori ichish vaqti"
        }
        val text = if (reminderType == REMINDER_TYPE_FOLLOW_UP) {
            "$medicineName dorisini ichdingizmi? 1 soat oldin eslatma yuborilgan edi."
        } else {
            "$medicineName dorisini ichish vaqti bo'ldi."
        }

        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0),
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0),
            notification,
        )
    }

    private fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Dori eslatmalari",
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = "Dori ichish vaqti uchun bildirishnomalar"
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 500, 200, 500)
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "medicine_reminders"
        const val EXTRA_MEDICINE_NAME = "extra_medicine_name"
        const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
        const val EXTRA_REMINDER_TYPE = "extra_reminder_type"
        const val REMINDER_TYPE_PRIMARY = "primary"
        const val REMINDER_TYPE_FOLLOW_UP = "follow_up"
    }
}
