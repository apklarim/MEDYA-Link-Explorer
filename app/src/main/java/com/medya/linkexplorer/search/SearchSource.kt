package com.medya.linkexplorer.search

data class SearchSource(

    val id: Long = System.currentTimeMillis(),

    val name: String,

    val url: String,

    val enabled: Boolean = true,

    val priority: Int = 0,

    val lastScanTime: Long = 0L,

    val lastSuccessTime: Long = 0L,

    val lastError: String = "",

    val scanCount: Int = 0
)
