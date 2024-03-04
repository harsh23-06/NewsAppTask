
package com.example.newsapptask
/*
 * FirebaseMessageReceiver is a class extending FirebaseMessagingService,
 * which receives Firebase Cloud Messaging (FCM) messages. It handles the
 * creation and display of notifications when a new FCM message is received.
 *
 * The onNewToken method is called when a new FCM token is generated for the
 * device, and it can be overridden to perform custom handling of the token.
 *
 * The onMessageReceived method is called when a new FCM message is received.
 * It checks if the message contains a notification payload, extracts the title
 * and body of the notification, and then calls the showNotification function
 * to display the notification.
 *
 * The showNotification function creates and displays a custom notification
 * using NotificationCompat.Builder. It creates a PendingIntent to open the
 * MainActivity when the notification is tapped. It also sets up vibration
 * and channel information for the notification.
 *
 * The getCustomDesign function creates a RemoteViews object to customize the
 * layout of the notification. It inflates a layout resource containing the
 * notification's title, description, and app logo.
 */
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.newsapptask.MainActivity
import com.example.newsapptask.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channel_id = "notification_channel"
const val channelName = "com.example.newsapptask"

class FirebaseMessageReceiver : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            showNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!
            )
        }
    }

    private fun getCustomDesign(
        title: String,
        message: String
    ): RemoteViews {
        val remoteViews = RemoteViews(
            "com.example.newsapptask",
            R.layout.notification
        )
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.description, message)
        remoteViews.setImageViewResource(
            R.id.appLogo,
            R.drawable.ic_stat_ic_notification
        )
        return remoteViews
    }

    private fun showNotification(
        title: String,
        message: String
    ) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            channel_id
        )
            .setSmallIcon(R.drawable.ic_stat_ic_notification)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(
            getCustomDesign(title, message)
        )

        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager?

        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                channel_id, channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager!!.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager!!.notify(0, builder.build())
    }
}
