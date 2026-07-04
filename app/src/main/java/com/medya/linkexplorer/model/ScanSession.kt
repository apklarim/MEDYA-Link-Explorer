package com.medya.linkexplorer.model

data class ScanSession(

    val id: Long = System.currentTimeMillis(),

    val startUrl: String,

    val startTime: Long = System.currentTimeMillis(),

    var endTime: Long = 0L,

    var scannedPages: Int = 0,

    var discoveredLinks: Int = 0,

    var htmlPages: Int = 0,

    var m3uLinks: Int = 0,

    var m3u8Links: Int = 0,

    var xtreamLinks: Int = 0,

    var magLinks: Int = 0,

    var jsonLinks: Int = 0,

    var xmlLinks: Int = 0,

    var zipLinks: Int = 0,

    var imageLinks: Int = 0,

    var videoLinks: Int = 0,

    var audioLinks: Int = 0,

    var errorCount: Int = 0
)
