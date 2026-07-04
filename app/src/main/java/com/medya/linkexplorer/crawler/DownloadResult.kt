package com.medya.linkexplorer.crawler

data class DownloadResult(

    val success: Boolean,

    val url: String,

    val html: String,

    val statusCode: Int,

    val error: String?
)
