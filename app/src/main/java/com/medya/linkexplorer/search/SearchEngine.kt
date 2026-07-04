package com.medya.linkexplorer.search

import com.medya.linkexplorer.crawler.CrawledLink
import com.medya.linkexplorer.crawler.WebCrawler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchEngine(
    private val crawler: WebCrawler = WebCrawler(),
    private val discovery: SourceDiscoveryEngine = SourceDiscoveryEngine()
) {

    suspend fun startScan(
        maxPages: Int = 100
    ): List<CrawledLink> = withContext(Dispatchers.IO) {

        val results = mutableListOf<CrawledLink>()

        discovery.getSources().forEach { source ->

            try {

                val links = crawler.crawl(
                    startUrl = source.url,
                    maxPages = maxPages
                )

                results.addAll(links)

            } catch (_: Exception) {
                // Bir kaynak başarısız olursa diğer kaynaklarla devam et.
            }
        }

        results
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
