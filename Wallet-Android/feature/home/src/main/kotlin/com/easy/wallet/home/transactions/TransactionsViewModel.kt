package com.easy.wallet.home.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.home.navigation.TokenArgs
import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.GetToKenBasicInfoUseCase
import com.easy.wallet.shared.domain.TokenBalanceUseCase
import com.easy.wallet.shared.domain.TransactionPagerUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

internal class TransactionsViewModel(
    savedStateHandle: SavedStateHandle,
    basicInfoUseCase: GetToKenBasicInfoUseCase,
    tokenBalanceUseCase: TokenBalanceUseCase,
    coinTrendUseCase: CoinTrendUseCase,
    tnxPagerUseCase: TransactionPagerUseCase
) : BaseViewModel<TransactionEvent>() {
    private val tokenArgs: TokenArgs = TokenArgs(savedStateHandle)
    private val tokenId = tokenArgs.tokenId

    val dashboardUiState = combine(
        basicInfoUseCase(tokenId),
        tokenBalanceUseCase(tokenId),
        coinTrendUseCase(tokenId)
    ) { basicInfo, amount, trends ->
        TransactionDashboardUiState.Success(
            basicInfo,
            amount,
            trends
        ) as TransactionDashboardUiState
    }.catch {
        emit(TransactionDashboardUiState.Error)
    }.stateIn(viewModelScope, SharingStarted.Lazily, TransactionDashboardUiState.Loading)

    val transactionPager = tnxPagerUseCase(tokenId).distinctUntilChanged()
        .catch { emit(PagingData.empty()) }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PagingData.empty())

    override fun handleEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.ClickReceive -> dispatchEvent(event)
            is TransactionEvent.ClickSend -> dispatchEvent(TransactionEvent.ClickSend(tokenId))
            is TransactionEvent.PopBack -> dispatchEvent(event)
        }
    }
}
