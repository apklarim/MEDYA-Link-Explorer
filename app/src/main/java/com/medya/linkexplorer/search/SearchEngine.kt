package com.medya.linkexplorer.search

import com.medya.linkexplorer.crawler.CrawledLink
import com.medya.linkexplorer.crawler.WebCrawler

class SearchEngine(
    private val crawler: WebCrawler = WebCrawler(),
    private val discovery: SourceDiscoveryEngine = SourceDiscoveryEngine()
) {

    suspend fun startScan(
        maxPages: Int = 100
    ): List<CrawledLink> {

        val results = mutableListOf<CrawledLink>()

        discovery.getSources().forEach { source ->

            val links = crawler.crawl(
                startUrl = source.url,
                maxPages = maxPages
            )

            results.addAll(links)
        }

        return results
            .distinctBy { it.url }
            .sortedBy { it.url }
    }

    fun addSource(
        name: String,
        url: String,
        priority: Int = 0
    ) {
        discovery.addSource(
            name = name,
            url = url,
            priority = priority
        )
    }

    fun removeSource(url: String) {
        discovery.removeSource(url)
    }

    fun clearSources() {
        discovery.clear()
    }

    fun getSources(): List<SearchSource> {
        return discovery.getSources()
    }
}
