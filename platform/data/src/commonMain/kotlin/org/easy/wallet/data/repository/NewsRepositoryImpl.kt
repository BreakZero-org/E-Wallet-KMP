package org.easy.wallet.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.easy.wallet.data.paging.NewsPagingSource
import org.easy.wallet.model.News
import org.easy.wallet.network.source.BlockChairController

class NewsRepositoryImpl internal constructor(
  private val blockChairController: BlockChairController
) : NewsRepository {
  override fun getNews(): Flow<PagingData<News>> {
    val pager = Pager(
      config = PagingConfig(NewsPagingSource.PAGE_SIZE, prefetchDistance = 2),
      pagingSourceFactory = { NewsPagingSource(blockChairController) }
    )
    return pager.flow
  }
}