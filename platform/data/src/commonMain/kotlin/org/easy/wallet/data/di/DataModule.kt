package org.easy.wallet.data.di

import org.easy.wallet.data.paging.NewsPagingSource
import org.easy.wallet.data.repository.NewsRepository
import org.easy.wallet.data.repository.NewsRepositoryImpl
import org.easy.wallet.network.di.networkModule
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {

  includes(networkModule)

  single { NewsRepositoryImpl(get()) } bind NewsRepository::class

  factory { NewsPagingSource(get()) }
}