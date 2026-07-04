package com.medya.linkexplorer.crawler

data class CrawledLink(

    val sourcePage: String,

    val url: String,

    val type: LinkType
)
