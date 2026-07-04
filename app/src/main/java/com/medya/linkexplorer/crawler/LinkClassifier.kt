package com.medya.linkexplorer.crawler

import com.medya.linkexplorer.model.LinkType

class LinkClassifier {

    fun classify(url: String): LinkType {

        val lower = url.lowercase()

        return when {

            lower.endsWith(".m3u") ->
                LinkType.M3U

            lower.endsWith(".m3u8") ->
                LinkType.M3U8

            lower.contains("/player_api.php") ->
                LinkType.XTREAM

            lower.contains("/portal.php") ||
            lower.contains("/stalker_portal") ->
                LinkType.MAG

            lower.endsWith(".json") ->
                LinkType.JSON

            lower.endsWith(".xml") ->
                LinkType.XML

            lower.endsWith(".zip") ->
                LinkType.ZIP

            lower.endsWith(".jpg") ||
            lower.endsWith(".jpeg") ||
            lower.endsWith(".png") ||
            lower.endsWith(".webp") ->
                LinkType.IMAGE

            lower.endsWith(".mp4") ||
            lower.endsWith(".ts") ->
                LinkType.VIDEO

            lower.endsWith(".mp3") ->
                LinkType.AUDIO

            lower.startsWith("http://") ||
            lower.startsWith("https://") ->
                LinkType.HTML

            else ->
                LinkType.UNKNOWN
        }
    }
}
