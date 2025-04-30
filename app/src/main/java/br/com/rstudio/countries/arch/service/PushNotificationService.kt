package br.com.rstudio.countries.arch.service

import br.com.rstudio.countries.arch.notification.NotificationHelper
import br.com.rstudio.countries.arch.util.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject
import timber.log.Timber

class PushNotificationService : FirebaseMessagingService() {

  private val notificationHelper: NotificationHelper by inject()

  override fun onMessageReceived(message: RemoteMessage) {
    super.onMessageReceived(message)
    notificationHelper.handleNotification(message)
  }

  override fun onNewToken(token: String) {
    super.onNewToken(token)
    Timber.tag(Constants.NOTIFICATION).d("Refreshed token: $token")
  }

  override fun onDeletedMessages() {
    super.onDeletedMessages()
    Timber.tag(Constants.NOTIFICATION).d("Deleted messages")
  }
}
