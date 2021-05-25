package com.adademy.discovery.repository

import com.adademy.discovery.cache.LatestEpisodesDao
import com.adademy.discovery.model.Episode
import com.adademy.discovery.network.DiscoveryService

class LatestEpisodesRepository(
    private val discoveryService: DiscoveryService,
    private val latestEpisodesDao: LatestEpisodesDao
) {
    suspend fun getLatestEpisodes(): List<Episode> {
        discoveryService.getLatestEpisodes()?.data?.media?.let {
            save(it)
            return it
        }
        return emptyList()
    }

    private suspend fun save(episodes: List<Episode>?) {
        episodes?.let {
            latestEpisodesDao.insertRows(it)
        }
    }

    suspend fun getCachedEpisodes(): List<Episode> {
        return latestEpisodesDao.getAllRows()
    }
}