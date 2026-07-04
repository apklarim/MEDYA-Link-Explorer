package com.medya.linkexplorer.crawler

import java.util.LinkedList
import java.util.Queue

class CrawlerEngine {

    private val visitedUrls = mutableSetOf<String>()
    private val urlQueue: Queue<String> = LinkedList()

    fun start(startUrl: String) {
        clear()

        if (startUrl.isBlank()) return

        urlQueue.add(startUrl)
    }

    fun hasNext(): Boolean {
        return urlQueue.isNotEmpty()
    }

    fun nextUrl(): String? {

        while (urlQueue.isNotEmpty()) {

            val url = urlQueue.poll()

            if (visitedUrls.add(url)) {
                return url
            }
        }

        return null
    }

    fun addUrls(urls: List<String>) {

        urls.forEach { url ->

            if (!visitedUrls.contains(url)) {
                urlQueue.offer(url)
            }
        }
    }

    fun visitedCount(): Int = visitedUrls.size

    fun queuedCount(): Int = urlQueue.size

    fun clear() {
        visitedUrls.clear()
        urlQueue.clear()
    }
}
