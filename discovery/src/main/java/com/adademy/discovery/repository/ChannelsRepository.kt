package com.adademy.discovery.repository

import com.adademy.discovery.cache.ChannelsDao
import com.adademy.discovery.model.Channel
import com.adademy.discovery.network.DiscoveryService

class ChannelsRepository(
    private val discoveryService: DiscoveryService,
    private val channelsDao: ChannelsDao
) {
    suspend fun getChannels(): List<Channel> {
        discoveryService.getChannels()?.data?.channels?.let {
            save(it)
            return it
        }
        return getCachedChannels()
    }

    private suspend fun save(channelList: List<Channel>?) {
        channelList?.let {
            channelsDao.insertRows(it)
        }
    }

    private suspend fun getCachedChannels(): List<Channel> {
        return channelsDao.getAllRows()
    }
}