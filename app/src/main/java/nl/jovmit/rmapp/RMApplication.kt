package nl.jovmit.rmapp

import android.app.Application
import nl.jovmit.rmapp.domain.domainModule
import nl.jovmit.rmapp.ui.artworks.artWorksModule
import nl.jovmit.rmapp.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RMApplication: Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger(Level.DEBUG)
      androidContext(this@RMApplication)
      modules(
        appModule,
        networkModule,
        domainModule,
        artWorksModule
      )
    }
  }
}