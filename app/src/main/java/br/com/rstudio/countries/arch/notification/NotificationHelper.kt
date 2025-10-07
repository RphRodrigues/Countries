package br.com.rstudio.countries.arch.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.util.Constants
import br.com.rstudio.countries.presentation.MainActivity
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class NotificationHelper(context: Context) {

  private val appContext = context.applicationContext

  private val notificationManager: NotificationManager =
    appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

  init {
    setUpNotificationChannel()
  }

  fun setUpNotificationChannel() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

    val channelName = appContext.getString(R.string.notification_channel_name)

    if (notificationManager.getNotificationChannel(channelName) == null) {
      val descriptionText = appContext.getString(R.string.notification_channel_description)
      val importance = NotificationManager.IMPORTANCE_HIGH

      val channel = NotificationChannel(
        appContext.getString(R.string.notification_channel_name), channelName, importance
      ).apply {
        description = descriptionText
      }

      notificationManager.createNotificationChannel(channel)
    }
  }

  fun showNotification(title: String, body: String) {
    val notificationId = "countries".hashCode()
    val channelName = appContext.getString(R.string.notification_channel_name)

    val builder = NotificationCompat.Builder(appContext, channelName)
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentTitle(title)
      .setContentText(body)
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setContentIntent(
        PendingIntent.getActivity(
          appContext,
          REQUEST_CONTENT,
          Intent(appContext, MainActivity::class.java),
          PendingIntent.FLAG_IMMUTABLE
        )
      )
      .setAutoCancel(true)
      .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
      .setOnlyAlertOnce(true)

    with(NotificationManagerCompat.from(appContext)) {
      if (areNotificationsEnabled()) {
        notify(notificationId, builder.build())
      }
    }
  }

  fun handleNotification(message: RemoteMessage) {
    message.notification?.run {
      val channelName = appContext.getString(R.string.notification_channel_name)

      val bigTextStyle = NotificationCompat.BigTextStyle()
        .bigText(body)

      val builder = NotificationCompat.Builder(appContext, channelName)
        .setSmallIcon(R.mipmap.ic_launcher)
        .apply {
          Timber.tag(Constants.NOTIFICATION).d("FCM Notification title: $title")
          if (title != null) {
            setContentTitle(title)
          }
        }
        .apply {
          Timber.tag(Constants.NOTIFICATION).d("FCM Notification body: $body")
          if (body != null) {
            setContentText(body)
          }
        }
        .setStyle(bigTextStyle)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(
          PendingIntent.getActivity(
            appContext,
            REQUEST_CONTENT,
            Intent(appContext, MainActivity::class.java).apply {
              action = Intent.ACTION_VIEW
              data = message.data["deeplink"]?.toUri()

              Timber.tag(Constants.DEEP_LINK).d("data: $data")
            },
            PendingIntent.FLAG_IMMUTABLE
          )
        )
        .setAutoCancel(true)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setOnlyAlertOnce(true)

      with(NotificationManagerCompat.from(appContext)) {
        if (areNotificationsEnabled()) {
          notify(message.messageId.hashCode(), builder.build())
        }
      }
    }
  }

  companion object {
    private const val REQUEST_CONTENT = 1
  }
}
