package com.medya.linkexplorer.crawler

import com.medya.linkexplorer.model.LinkType

data class CrawledLink(

    val sourcePage: String,

    val url: String,

    val type: LinkType

)
