package com.medya.linkexplorer.search

import com.medya.linkexplorer.crawler.CrawledLink
import com.medya.linkexplorer.crawler.WebCrawler

class SearchEngine(
    private val crawler: WebCrawler = WebCrawler()
) {

    /**
     * Taranacak kaynaklar.
     * İleride Ayarlar ekranından düzenlenebilir olacak.
     */
    private val sources = mutableListOf<String>()

    fun addSource(url: String) {
        if (url.isNotBlank() && !sources.contains(url)) {
            sources.add(url)
        }
    }

    fun removeSource(url: String) {
        sources.remove(url)
    }

    fun clearSources() {
        sources.clear()
    }

    fun getSources(): List<String> {
        return sources.toList()
    }

    suspend fun startScan(
        maxPages: Int = 100
    ): List<CrawledLink> {

        val results = mutableListOf<CrawledLink>()

        for (source in sources) {

            val links = crawler.crawl(
                startUrl = source,
                maxPages = maxPages
            )

            results.addAll(links)
        }

        return results
            .distinctBy { it.url }
    }
}
