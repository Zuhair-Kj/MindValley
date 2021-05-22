package com.adademy.discovery.model

data class Channel(
    val title: String?,
    val series: List<Series>?,
    val mediaCount: Int,
    val latestMedia: List<Episode>?
) {
    enum class ChannelType {
        SERIES, COURSE
    }

    fun getChannelType(): ChannelType {
        if (series?.isNullOrEmpty() == false) return ChannelType.SERIES
        return ChannelType.COURSE
    }
}

data class ChannelsList(
    val channels: List<Channel>?
)

data class ChannelsResponse(
    val data: ChannelsList?
)