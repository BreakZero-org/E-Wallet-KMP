//
//  NewsScreen.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/16.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NewsScreen: View {
    @ObservedObject private var viewModel = ViewModel()
    
    var body: some View {
        List {
            ForEach(viewModel.newsResult, id: \.self.title) { news in
                Button(action: {
                    if let url = URL(string: news.link), UIApplication.shared.canOpenURL(url) {
                        UIApplication.shared.open(url)
                    }
                }, label: {
                    NewsView(news: news).onAppear {
                        
                    }
                })
            }
            
            if viewModel.hasNotMore {
                Text("-- Not more --")
                    .frame(maxWidth: .infinity, alignment: .center)
            } else {
                ProgressView()
            }
        }.listStyle(.plain)
            .task {
                await viewModel.loadNews()
            }
    }
}
