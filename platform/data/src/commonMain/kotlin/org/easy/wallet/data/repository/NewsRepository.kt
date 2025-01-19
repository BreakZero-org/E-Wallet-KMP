package org.easy.wallet.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.easy.wallet.model.News

interface NewsRepository {
  fun getNews(): Flow<PagingData<News>>
}