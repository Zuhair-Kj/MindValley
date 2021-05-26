package com.adademy.discovery

import com.adademy.discovery.cache.ChannelsDao
import com.adademy.discovery.model.Channel
import com.adademy.discovery.model.ChannelsList
import com.adademy.discovery.model.ChannelsResponse
import com.adademy.discovery.network.DiscoveryService
import com.adademy.discovery.repository.ChannelsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChannelsRepositoryTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var channelsRepository: ChannelsRepository

    @Mock
    lateinit var discoveryService: DiscoveryService

    @Mock
    lateinit var channelsDao: ChannelsDao

    @Before
    fun init() {
        channelsRepository = ChannelsRepository(discoveryService, channelsDao)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return results after saving to DB`() {
        val mockItems = listOf<Channel>(mock(Channel::class.java))
        testDispatcher.runBlockingTest {
            `when`(discoveryService.getChannels()).thenReturn(ChannelsResponse(ChannelsList(mockItems)))
            val channels = channelsRepository.getChannels()
            Mockito.verify(discoveryService).getChannels()
            Mockito.verify(channelsDao).insertRows(mockItems)
            Assert.assertEquals(channels, mockItems)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return cached result`() {
        val mockItems = listOf<Channel>(mock(Channel::class.java))
        testDispatcher.runBlockingTest {
            `when`(channelsDao.getAllRows()).thenReturn(mockItems)
            val channels = channelsRepository.getChannels()
            Mockito.verifyNoInteractions(discoveryService)
            Mockito.verify(channelsDao).getAllRows()
            Assert.assertEquals(channels, mockItems)
        }
    }
}