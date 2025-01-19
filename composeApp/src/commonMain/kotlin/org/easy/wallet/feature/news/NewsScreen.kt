package org.easy.wallet.feature.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import org.easy.wallet.components.PullToRefreshPagingColumn
import org.easy.wallet.model.News
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewsScreen() {
  val viewModel: NewsViewModel = koinViewModel()
  val news = viewModel.newsPagingData.collectAsLazyPagingItems()
  NewsTabScreen(news)
}

@Composable
private fun NewsTabScreen(newsPagingItems: LazyPagingItems<News>) {
  Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Red) {
    PullToRefreshPagingColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
      pagingItems = newsPagingItems,
      verticalArrangement = Arrangement.spacedBy(12.dp),
      itemKey = { index -> newsPagingItems[index]!!.hash },
      itemContainer = { news ->
        NewsItemView(modifier = Modifier.fillMaxWidth(), news = news, itemClick = {})
      }
    )
  }
}