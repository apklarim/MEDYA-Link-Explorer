package com.medya.linkexplorer.crawler

interface CrawlerListener {

    fun onStarted(startUrl: String)

    fun onPageStarted(url: String)

    fun onPageFinished(
        url: String,
        linksFound: Int
    )

    fun onLinkFound(link: CrawledLink)

    fun onProgress(
        visited: Int,
        queued: Int
    )

    fun onCompleted(
        totalLinks: Int
    )

    fun onError(
        url: String,
        message: String
    )
}
