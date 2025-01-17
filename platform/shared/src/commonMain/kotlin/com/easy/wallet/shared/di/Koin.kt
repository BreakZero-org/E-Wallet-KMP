package com.easy.wallet.shared.di

import com.easy.wallet.core.di.dispatcherModule
import com.easy.wallet.database.di.databaseModule
import com.easy.wallet.datastore.di.storageModule
import com.easy.wallet.datastore.di.userDefaultModule
import com.easy.wallet.network.di.networkModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication = startKoin {
  appDeclaration()
  modules(sharedModule)
  modules(dispatcherModule)
  modules(userDefaultModule())
  modules(storageModule())
  modules(networkModule)
  modules(databaseModule)
}
