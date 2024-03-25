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
                    NewsView(news: news)
                        .listRowInsets(EdgeInsets())
                        .listRowBackground(Color.clear)
                })
            }
            
            if viewModel.hasNextPage {
                ProgressView()
                    .onAppear {
                        viewModel.loadNextPage()
                    }
            } else {
                Text("-- Not more --")
                    .frame(maxWidth: .infinity, alignment: .center)
            }
        }.listStyle(.plain)
            .task {
                await viewModel.startLoadNews()
            }.task {
                await viewModel.subscrideDataChanged()
            }.task {
                await viewModel.subscrideLoadState()
            }
        
    }
}
