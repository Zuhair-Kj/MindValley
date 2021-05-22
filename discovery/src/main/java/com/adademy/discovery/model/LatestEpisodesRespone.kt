package com.adademy.discovery.model

data class LatestEpisodesRespone(
    val data: LatestEpisodesList?
)

data class LatestEpisodesList(
    val media: List<Episode>?
)