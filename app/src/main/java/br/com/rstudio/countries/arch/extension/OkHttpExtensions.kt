package br.com.rstudio.countries.arch.extension

import android.content.Context
import android.os.Build
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.util.Constants
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import okhttp3.OkHttpClient
import timber.log.Timber

/**
 * Extension function for OkHttpClient.Builder that manually adds Let's Encrypt
 * root certificates (ISRG Root X1 and ISRG Root X2) to the TrustManager.
 *
 * This is necessary for Android versions ≤ Nougat MR1 (7.1.1), which do not trust
 * these certificates by default, leading to SSL handshake failures when connecting
 * to servers using Let's Encrypt.
 *
 * @see <a href="https://stackoverflow.com/questions/64844311/certpathvalidatorexception-connecting-to-a-lets-encrypt-host-on-android-m-or-ea">
 * StackOverflow: CertPathValidatorException on older Android</a>
 */
@Suppress("MaxLineLength")
fun OkHttpClient.Builder.addTrustedCertForOldAndroid(context: Context) = apply {
  if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
    Timber.tag(Constants.HTTP).w("Adding certificates to ensure compatibility with Let's Encrypt.")

    runCatching {
      val cf = CertificateFactory.getInstance("X.509")

      val isrgRoot1Input = context.resources.openRawResource(R.raw.isrgrootx1)
      val isrgRoot1Certificate: Certificate = isrgRoot1Input.use {
        cf.generateCertificate(it)
      }

      val isrgRoot2Input = context.resources.openRawResource(R.raw.isrgrootx2)
      val isrgRoot2Certificate: Certificate = isrgRoot2Input.use {
        cf.generateCertificate(it)
      }

      val keyStoreType = KeyStore.getDefaultType()
      val keyStore = KeyStore.getInstance(keyStoreType).apply {
        load(null, null)
        setCertificateEntry("isrgrootx1", isrgRoot1Certificate)
        setCertificateEntry("isrgrootx2", isrgRoot2Certificate)
      }

      val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
      val tmf = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
        init(keyStore)
      }

      val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, tmf.trustManagers, null)
      }

      sslSocketFactory(sslContext.socketFactory, tmf.trustManagers[0] as X509TrustManager)
    }.onFailure {
      Timber.tag(Constants.HTTP).w(
        "Error adding ISRG X1 ISRG X2 certificates. Connections to Let's Encrypt-secured servers will not work. $it"
      )
    }
  }
}
