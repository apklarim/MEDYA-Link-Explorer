package com.medya.linkexplorer.crawler

import java.net.URI

class WebCrawler {

    private val crawlerEngine = CrawlerEngine()
    private val downloader = HtmlDownloader()
    private val parser = HtmlParser()
    private val classifier = LinkClassifier()
    private val filter = UrlFilter()
    private val robotsChecker = RobotsChecker()

    fun crawl(
        startUrl: String,
        maxPages: Int = 100
    ): List<CrawledLink> {

        val results = mutableListOf<CrawledLink>()

        crawlerEngine.start(startUrl)

        while (crawlerEngine.hasNext() &&
            crawlerEngine.visitedCount() < maxPages) {

            val currentUrl = crawlerEngine.nextUrl() ?: continue

            if (!filter.isValid(currentUrl))
                continue

            val robots = robotsChecker.download(currentUrl)

            val path = try {
                URI(currentUrl).path
            } catch (e: Exception) {
                "/"
            }

            if (!robotsChecker.isAllowed(robots, path))
                continue

            val download = downloader.download(currentUrl)

            if (!download.success)
                continue

            val document = parser.parse(download.html)

            val links = parser.getLinks(document)

            for (link in links) {

                if (!filter.isValid(link))
                    continue

                if (!filter.isSameDomain(startUrl, link))
                    continue

                if (filter.shouldSkip(link))
                    continue

                val type = classifier.classify(link)

                results.add(
                    CrawledLink(
                        sourcePage = currentUrl,
                        url = link,
                        type = type
                    )
                )

                crawlerEngine.addUrls(listOf(link))
            }
        }

        return results
    }
}
