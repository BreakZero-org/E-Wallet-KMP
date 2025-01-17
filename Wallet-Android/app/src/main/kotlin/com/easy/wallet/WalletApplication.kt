package com.easy.wallet

import android.app.Application
import com.easy.wallet.assetmanager.di.assetModule
import com.easy.wallet.di.appModule
import com.easy.wallet.discover.di.discoverModule
import com.easy.wallet.home.di.homeModule
import com.easy.wallet.marketplace.di.marketModule
import com.easy.wallet.news.di.newsModule
import com.easy.wallet.onboard.di.onboardModule
import com.easy.wallet.send.di.sendModule
import com.easy.wallet.settings.di.settingsModule
import com.easy.wallet.shared.di.initKoin
import org.koin.android.ext.koin.androidContext

class WalletApplication : Application() {
  init {
    System.loadLibrary("TrustWalletCore")
  }

  override fun onCreate() {
    super.onCreate()

    initKoin {
      androidContext(this@WalletApplication)
      modules(appModule)
      modules(homeModule)
      modules(newsModule)
      modules(discoverModule)
      modules(onboardModule)
      modules(marketModule)
      modules(settingsModule)
      modules(assetModule())
      modules(sendModule)
    }
  }
}
