package com.easy.wallet.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.model.news.News
import com.easy.wallet.news.component.NewItemView
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NewsRoute() {
    val viewModel: NewsViewModel = koinViewModel()
    val news = viewModel.newsUiState.collectAsLazyPagingItems()
    NewsScreen(newsPaging = news)
}

@Composable
fun NewsScreen(
    newsPaging: LazyPagingItems<News>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(newsPaging.itemCount) { index ->
            NewItemView(news = newsPaging[index]!!)
        }
        newsPaging.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingWheel(contentDesc = "")
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = { refresh() }) {
                                Text(text = "tap to refresh...")
                            }
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingWheel(contentDesc = "")
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = ::retry) {
                                Text(text = "load more failed...")
                            }
                        }
                    }
                }
            }
        }
    }
}