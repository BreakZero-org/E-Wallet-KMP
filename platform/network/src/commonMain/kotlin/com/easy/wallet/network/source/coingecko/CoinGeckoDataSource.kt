package com.easy.wallet.network.source.coingecko

import com.easy.wallet.network.source.coingecko.dto.CoinGeckoMarketChartDto
import com.easy.wallet.network.source.coingecko.dto.CoinGeckoMarketsDto
import com.easy.wallet.network.source.coingecko.dto.CoinGeckoSearchTrendingDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CoinGeckoDataSource internal constructor(
    private val httpClient: HttpClient
) : CoinGeckoApi {
    override suspend fun getCoinsMarkets(
        currency: String,
        page: Int,
        numCoinsPerPage: Int,
        order: String,
        includeSparkline7dData: Boolean,
        priceChangePercentageIntervals: String,
        coinIds: String?
    ): List<CoinGeckoMarketsDto> {
        val response = httpClient.get("coins/markets") {
            parameter("vs_currency", currency)
            parameter("page", page)
            parameter("per_page", numCoinsPerPage)
            parameter("order", order)
            parameter("sparkline", includeSparkline7dData)
            parameter("price_change_percentage", priceChangePercentageIntervals)
            parameter("ids", coinIds)
        }.body<List<CoinGeckoMarketsDto>>()
        return response
    }

    override suspend fun getCoinMarketChart(
        coinId: String,
        currency: String,
        days: String
    ): CoinGeckoMarketChartDto {
        val response = httpClient.get("coins/$coinId/market_chart") {
            parameter("vs_currency", currency)
            parameter("days", days)
        }.body<CoinGeckoMarketChartDto>()
        return response
    }

    override suspend fun getSearchTrending(): CoinGeckoSearchTrendingDto {
        return httpClient.get("search/trending").body<CoinGeckoSearchTrendingDto>()
    }
}
