package kz.just_code.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService:FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "FCM TOKEN: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showMessage(createNotification(
            message.data["title"], message.data["body"], message.data["sub_title"], message.data["btn_name"]))

    }

    private fun showMessage(notification: Notification) {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1010, notification)
    }

    private fun createNotification(title: String?, message: String?, subTitle: String? = null, name: String?): Notification {
        return NotificationCompat.Builder(baseContext, getChannelId())
            .setSmallIcon(R.drawable.baseline_check_circle_24)
            .setContentTitle(title)
            .setContentText(message)
            .setSubText(subTitle)
            .setOngoing(true)
            .setVibrate(LongArray(250))
            .setColor(ContextCompat.getColor(baseContext, R.color.grey))
            .build()
    }

    private fun getChannelId(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "MyNotificationId"
            val channelName = "NotificationsFCM"
            val channelDescription = "Channel for FCM notifications"
            val channelImportance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, channelImportance)
            channel.description = channelDescription

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            channelId
        } else ""
    }


}
