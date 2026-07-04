package com.medya.linkexplorer.crawler

import java.net.URI

class UrlFilter {

    /**
     * URL geçerli mi?
     */
    fun isValid(url: String): Boolean {

        if (url.isBlank()) return false

        return try {

            val uri = URI(url)

            (uri.scheme == "http" || uri.scheme == "https") &&
                    !uri.host.isNullOrBlank()

        } catch (e: Exception) {

            false
        }
    }

    /**
     * Aynı domaine mi ait?
     */
    fun isSameDomain(baseUrl: String, targetUrl: String): Boolean {

        return try {

            val base = URI(baseUrl)
            val target = URI(targetUrl)

            base.host.equals(target.host, ignoreCase = true)

        } catch (e: Exception) {

            false
        }
    }

    /**
     * İstenmeyen dosya türlerini ele
     */
    fun shouldSkip(url: String): Boolean {

        val lower = url.lowercase()

        return lower.endsWith(".jpg") ||
                lower.endsWith(".jpeg") ||
                lower.endsWith(".png") ||
                lower.endsWith(".gif") ||
                lower.endsWith(".bmp") ||
                lower.endsWith(".svg") ||
                lower.endsWith(".ico") ||
                lower.endsWith(".mp3") ||
                lower.endsWith(".mp4") ||
                lower.endsWith(".avi") ||
                lower.endsWith(".mov") ||
                lower.endsWith(".pdf")
    }

    /**
     * URL normalize edilir
     */
    fun normalize(url: String): String {

        return url
            .trim()
            .removeSuffix("/")
    }
}
