//
//  TransactionViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2024/3/25.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import AsyncAlgorithms
import KMPNativeCoroutinesAsync

extension TransactionScreen {
    @MainActor final class ViewModel: ObservableObject {

        private var delegate = PagingCollectionViewController<TransactionUiModel>()

        @LazyKoin private var tokenBalanceUseCase: TokenBalanceUseCase
        @LazyKoin private var coinTrendUseCase: CoinTrendUseCase
        @LazyKoin private var tnxPagerUseCase: TransactionPagerUseCase

        @Published private(set) var transactions:[TransactionUiModel] = []
        @Published private(set) var hasNextPage: Bool = false
        @Published private(set) var showLoading: Bool = false

        @Published private(set) var dashboardDesc: String = ""

        func loading(tokenId: String) async {
            let balanceSequence = asyncSequence(for: tokenBalanceUseCase.invoke(tokenId: tokenId))
            let trendsSequence = asyncSequence(for: coinTrendUseCase.invoke(tokenId: tokenId))
            do {
                try await combineLatest(balanceSequence, trendsSequence).collect { balance, trends in
                    print("====== \(balance), trends: \(trends)")
                    self.dashboardDesc = "====== \(balance), trends: \(trends)"
                }
            } catch {
                print(error)
            }
        }

        func initPaging(tokenId: String) async {
            let transactionStream = tnxPagerUseCase.invoke(tokenId: tokenId)
            try? await asyncSequence(for: transactionStream).collect { pagingData in
                print(pagingData.description)
                try? await delegate.submitData(pagingData: pagingData)
            }
        }
        
        func loadNextPage() {
            delegate.loadNextPage()
        }

        func subscribeDataChanged() async {
            do {
                for try await _ in asyncSequence(for: delegate.onPagesUpdatedFlow) {
                    self.transactions = delegate.getItems()
                }
            } catch {
                
            }
        }

        func subscribeLoadStateChanged() async {
            do {
                for try await loadState in asyncSequence(for: delegate.loadStateFlow) {
                    switch onEnum(of: loadState.append) {
                    case .error(let errorState):
                        print(errorState.error.message?.description ?? "append error...")
                        break
                    case .loading(_):
                        break
                    case .notLoading(let notLoading):
                        self.hasNextPage = !notLoading.endOfPaginationReached
                        break
                    }
                    
                    switch onEnum(of: loadState.refresh) {
                    case .error(_):
                        break
                    case .loading(_):
                        self.showLoading = true
                        break
                    case .notLoading(_):
                        self.showLoading = false
                        break
                    }
                }
            } catch {
                
            }
        }
    }
}
