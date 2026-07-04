package com.medya.linkexplorer.repository

import com.medya.linkexplorer.crawler.CrawledLink
import com.medya.linkexplorer.crawler.CrawlerListener
import com.medya.linkexplorer.crawler.WebCrawler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CrawlerRepository(
    private val crawler: WebCrawler = WebCrawler()
) {

    suspend fun startScan(
        startUrl: String,
        maxPages: Int = 100,
        listener: CrawlerListener? = null
    ): List<CrawledLink> = withContext(Dispatchers.IO) {

        listener?.onStarted(startUrl)

        return@withContext try {

            val result = crawler.crawl(
                startUrl = startUrl,
                maxPages = maxPages
            )

            result.forEach { link ->
                listener?.onLinkFound(link)
            }

            listener?.onCompleted(result.size)

            result

        } catch (e: Exception) {

            listener?.onError(
                startUrl,
                e.message ?: "Unknown error"
            )

            emptyList()
        }
    }
}
