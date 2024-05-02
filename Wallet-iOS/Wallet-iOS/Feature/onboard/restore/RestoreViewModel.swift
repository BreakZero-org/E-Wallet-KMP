//
//  RestoreViewModel.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/20.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

extension RestoreScreen {
    @MainActor final class ViewModel: ObservableObject {
        
        @LazyKoin private var multiWalletRepository: MultiWalletRepository
        
        private var insertTask: Task<Void, Never>? = nil
        
        @Published var seedPhrase: String = ""
        @Published var password: String = ""
        @Published var confirmPassword: String = ""
        
        func restoreWallet(
            onCompletion: @escaping () -> Void
        ) {
            insertTask = Task {
                do {
                    let _ = try await asyncFunction(for: multiWalletRepository.insertOne(mnemonic: seedPhrase, passphrase: "", onCompleted: onCompletion))
                } catch {
                    print("Failed with error: \(error)")
                }
            }
        }
        func onCleaned() {
            insertTask?.cancel()
            insertTask = nil
        }
    }
}
