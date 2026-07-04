package com.medya.linkexplorer.crawler

import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URI
import java.util.concurrent.TimeUnit

class RobotsChecker {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .followRedirects(true)
        .build()

    /**
     * robots.txt dosyasını indirir.
     */
    fun download(baseUrl: String): String? {

        return try {

            val uri = URI(baseUrl)

            val robotsUrl =
                "${uri.scheme}://${uri.host}/robots.txt"

            val request = Request.Builder()
                .url(robotsUrl)
                .header(
                    "User-Agent",
                    "MEDYA Link Explorer/1.0"
                )
                .build()

            client.newCall(request)
                .execute()
                .use { response ->

                    if (!response.isSuccessful)
                        return null

                    response.body?.string()
                }

        } catch (e: Exception) {

            null
        }
    }

    /**
     * Taramaya izin veriliyor mu?
     */
    fun isAllowed(
        robotsTxt: String?,
        path: String
    ): Boolean {

        if (robotsTxt.isNullOrBlank())
            return true

        val lines = robotsTxt.lines()

        for (line in lines) {

            val text = line.trim()

            if (text.startsWith("Disallow:", true)) {

                val rule = text
                    .substringAfter(":")
                    .trim()

                if (rule.isNotBlank() &&
                    path.startsWith(rule)
                ) {
                    return false
                }
            }
        }

        return true
    }
}
