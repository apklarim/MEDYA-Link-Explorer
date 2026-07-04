package com.medya.linkexplorer.crawler

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class HtmlDownloader {

    private val client = OkHttpClient()

    fun download(url: String): Result<String> {
        return try {
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "MEDYA Link Explorer")
                .build()

            client.newCall(request).execute().use { response ->

                if (!response.isSuccessful) {
                    return Result.failure(IOException("HTTP ${response.code}"))
                }

                Result.success(response.body?.string().orEmpty())
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
