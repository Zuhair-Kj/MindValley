package com.adademy.discovery

import com.adademy.discovery.cache.LatestEpisodesDao
import com.adademy.discovery.model.Episode
import com.adademy.discovery.model.LatestEpisodesList
import com.adademy.discovery.model.LatestEpisodesRespone
import com.adademy.discovery.network.DiscoveryService
import com.adademy.discovery.repository.LatestEpisodesRepository
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LatestEpisodesRepositoryTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var discoveryService: DiscoveryService

    @Mock
    lateinit var latestEpisodesDao: LatestEpisodesDao

    private lateinit var repository: LatestEpisodesRepository

    @Before
    fun init() {
        repository = LatestEpisodesRepository(discoveryService, latestEpisodesDao)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return results from api after saving to DB`() {
        val mockedItems = listOf<Episode>(mock(Episode::class.java))

        testDispatcher.runBlockingTest {
            `when`(discoveryService.getLatestEpisodes()).thenReturn(LatestEpisodesRespone(LatestEpisodesList(mockedItems)))
            val episodes = repository.getLatestEpisodes()

            verify(discoveryService).getLatestEpisodes()
            verify(latestEpisodesDao).insertRows(mockedItems)
            Assert.assertEquals(episodes, mockedItems)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return results from DB`() {
        val mockedItems = listOf<Episode>(mock(Episode::class.java))
        testDispatcher.runBlockingTest {
            `when`(latestEpisodesDao.getAllRows()).thenReturn(mockedItems)
            val episodes = repository.getCachedEpisodes()

            verifyNoInteractions(discoveryService)
            verify(latestEpisodesDao).getAllRows()
            Assert.assertEquals(episodes, mockedItems)
        }
    }
}