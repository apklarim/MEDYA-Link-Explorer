package com.medya.linkexplorer.crawler

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class HtmlDownloader {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .followRedirects(true)
        .build()

    fun download(url: String): DownloadResult {

        return try {

            val request = Request.Builder()
                .url(url)
                .header(
                    "User-Agent",
                    "MEDYA Link Explorer/1.0"
                )
                .build()

            client.newCall(request).execute().use { response ->

                if (!response.isSuccessful) {

                    return DownloadResult(
                        success = false,
                        url = url,
                        html = "",
                        statusCode = response.code,
                        error = "HTTP ${response.code}"
                    )
                }

                DownloadResult(
                    success = true,
                    url = url,
                    html = response.body?.string().orEmpty(),
                    statusCode = response.code,
                    error = null
                )
            }

        } catch (e: IOException) {

            DownloadResult(
                success = false,
                url = url,
                html = "",
                statusCode = -1,
                error = e.message
            )
        }
    }
}
