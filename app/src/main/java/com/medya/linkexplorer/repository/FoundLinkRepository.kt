package com.medya.linkexplorer.repository

import com.medya.linkexplorer.crawler.CrawledLink

object FoundLinkRepository {

    private val links = mutableListOf<CrawledLink>()

    @Synchronized
    fun add(link: CrawledLink) {

        if (links.none { it.url.equals(link.url, true) }) {
            links.add(link)
        }
    }

    @Synchronized
    fun addAll(newLinks: List<CrawledLink>) {

        newLinks.forEach {
            add(it)
        }
    }

    @Synchronized
    fun getAll(): List<CrawledLink> {

        return links.toList()
    }

    @Synchronized
    fun clear() {

        links.clear()
    }

    @Synchronized
    fun size(): Int {

        return links.size
    }

    @Synchronized
    fun getByType(type: String): List<CrawledLink> {

        return links.filter {

            it.type.name.equals(type, true)

        }
    }
}
