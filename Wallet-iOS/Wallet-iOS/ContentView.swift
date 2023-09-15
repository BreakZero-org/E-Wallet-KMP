import SwiftUI

struct ContentView: View {
    
    var body: some View {
        TabView {
            HomeScreen().tabItem {
                Label(
                    title: { Text("Home") },
                    icon: { Image("home") }
                )
            }
            MarketplaceScreen().tabItem {
                Label(
                    title: { Text("Marketplace") },
                    icon: { Image("store") }
                )
            }
            DiscoverScreen().tabItem {
                Label(
                    title: { Text("Discover") },
                    icon: { Image("travel_explore") }
                )
            }
        }
    }
}

#Preview {
    ContentView()
}
