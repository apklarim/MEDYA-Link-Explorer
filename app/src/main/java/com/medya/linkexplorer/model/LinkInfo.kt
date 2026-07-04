package com.medya.linkexplorer.model

data class LinkInfo(

    val id: Long = 0L,

    val sourcePage: String,

    val url: String,

    val type: LinkType,

    val title: String = "",

    val statusCode: Int = 0,

    val contentType: String = "",

    val contentLength: Long = 0L,

    val discoveredTime: Long = System.currentTimeMillis(),

    val verified: Boolean = false,

    val favorite: Boolean = false,

    val notes: String = ""
)
