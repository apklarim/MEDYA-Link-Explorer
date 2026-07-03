package com.medya.linkexplorer

data class M3uChannel(
    val name: String,
    val url: String,
    val group: String = "",
    val logo: String = ""
)

object M3uParser {

    fun parse(lines: List<String>): List<M3uChannel> {

        val channels = mutableListOf<M3uChannel>()

        var currentName = ""
        var currentGroup = ""
        var currentLogo = ""

        for (line in lines) {

            when {

                line.startsWith("#EXTINF") -> {

                    currentName = line.substringAfter(",").trim()

                    currentGroup = Regex("""group-title="([^"]*)"""")
                        .find(line)
                        ?.groupValues
                        ?.get(1)
                        ?: ""

                    currentLogo = Regex("""tvg-logo="([^"]*)"""")
                        .find(line)
                        ?.groupValues
                        ?.get(1)
                        ?: ""
                }

                line.startsWith("http://") ||
                line.startsWith("https://") -> {

                    channels.add(
                        M3uChannel(
                            name = currentName,
                            url = line.trim(),
                            group = currentGroup,
                            logo = currentLogo
                        )
                    )
                }
            }
        }

        return channels
    }
                                        }
