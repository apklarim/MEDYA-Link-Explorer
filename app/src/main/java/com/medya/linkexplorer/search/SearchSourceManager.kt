package com.medya.linkexplorer.search

class SearchSourceManager {

    private val sources = mutableListOf<SearchSource>()

    fun add(source: SearchSource) {

        if (sources.none { it.url == source.url }) {
            sources.add(source)
        }
    }

    fun remove(url: String) {

        sources.removeAll {

            it.url.equals(url, true)

        }
    }

    fun clear() {

        sources.clear()

    }

    fun getAll(): List<SearchSource> {

        return sources.sortedBy {

            it.priority

        }

    }

    fun getEnabled(): List<SearchSource> {

        return sources
            .filter {

                it.enabled

            }
            .sortedBy {

                it.priority

            }
    }

    fun size(): Int {

        return sources.size

    }
}
