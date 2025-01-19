package org.easy.wallet.di

import org.easy.wallet.feature.news.NewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { NewsViewModel(get()) }
}