package com.medya.linkexplorer.crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HtmlParser {

    fun parse(html: String): Document {
        return Jsoup.parse(html)
    }

    fun getTitle(document: Document): String {
        return document.title().trim()
    }

    fun getMetaDescription(document: Document): String {

        return document.select("meta[name=description]")
            .attr("content")
            .trim()
    }

    fun getLinks(document: Document): List<String> {

        return document
            .select("a[href]")
            .mapNotNull {

                val url = it.absUrl("href")

                if (url.isBlank()) null else url

            }
            .distinct()
    }

    fun getScripts(document: Document): List<String> {

        return document
            .select("script[src]")
            .mapNotNull {

                val url = it.absUrl("src")

                if (url.isBlank()) null else url

            }
            .distinct()
    }

    fun getImages(document: Document): List<String> {

        return document
            .select("img[src]")
            .mapNotNull {

                val url = it.absUrl("src")

                if (url.isBlank()) null else url

            }
            .distinct()
    }

    fun getStyles(document: Document): List<String> {

        return document
            .select("link[href]")
            .mapNotNull {

                val url = it.absUrl("href")

                if (url.isBlank()) null else url

            }
            .distinct()
    }
}
