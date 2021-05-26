package com.adademy.discovery

import androidx.lifecycle.MutableLiveData
import com.adademy.core.util.NetworkHelper
import com.adademy.core.util.Resource
import com.adademy.discovery.repository.CategoriesRepository
import com.adademy.discovery.repository.ChannelsRepository
import com.adademy.discovery.repository.LatestEpisodesRepository
import com.adademy.discovery.viewmodel.DiscoveryViewModel
import com.adademy.discovery.viewmodel.DiscoveryViewModel.DiscoveryState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adademy.discovery.model.Category
import com.adademy.discovery.model.Channel
import com.adademy.discovery.model.Episode
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class DiscoveryViewModelTest {

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Mock lateinit var channelsRepository: ChannelsRepository

    @Mock lateinit var categoriesRepository: CategoriesRepository

    @Mock lateinit var latestEpisodesRepository: LatestEpisodesRepository
    @Mock lateinit var networkHelper: NetworkHelper

    private val mutableLiveData = MutableLiveData<Resource<DiscoveryState>>()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope()

    private lateinit var viewModel: DiscoveryViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun init() {
        viewModel = DiscoveryViewModel(channelsRepository, categoriesRepository, latestEpisodesRepository,
            networkHelper, mutableLiveData, testDispatcher, testScope)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should fetch results from API`() {
        mutableLiveData.postValue(Resource.loading())
        val channels = listOf(mock(Channel::class.java))
        val categories = listOf(mock(Category::class.java))
        val latestEpisodes = listOf(mock(Episode::class.java))
        `when`(networkHelper.isConnected()).thenReturn(true)
        testDispatcher.runBlockingTest {
            `when`(categoriesRepository.getCategories()).thenReturn(categories)
            `when`(channelsRepository.getChannels()).thenReturn(channels)
            `when`(latestEpisodesRepository.getLatestEpisodes()).thenReturn(latestEpisodes)
            viewModel.fetchRows()
            Assert.assertEquals(Resource.success(DiscoveryState(latestEpisodes, channels, categories)), mutableLiveData.value)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should post loadingState preserving old data`() {
        val channels = listOf(mock(Channel::class.java))
        val categories = listOf(mock(Category::class.java))
        val latestEpisodes = listOf(mock(Episode::class.java))

        val oldState = DiscoveryState(latestEpisodes, channels, categories)
        mutableLiveData.postValue(Resource.success(oldState))

        viewModel.propagateLoadingState()
        Assert.assertEquals(Resource.loading(oldState), mutableLiveData.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should get data from cache`() {
        mutableLiveData.postValue(Resource.loading())
        val channels = listOf(mock(Channel::class.java))
        val categories = listOf(mock(Category::class.java))
        val latestEpisodes = listOf(mock(Episode::class.java))
        `when`(networkHelper.isConnected()).thenReturn(false)
        testDispatcher.runBlockingTest {
            `when`(categoriesRepository.getCachedCategories()).thenReturn(categories)
            `when`(channelsRepository.getCachedChannels()).thenReturn(channels)
            `when`(latestEpisodesRepository.getCachedEpisodes()).thenReturn(latestEpisodes)
            viewModel.fetchRows()
            Assert.assertEquals(Resource.networkError(DiscoveryState(latestEpisodes, channels, categories)), mutableLiveData.value)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should handle error thrown gracefully`() {
        val state = DiscoveryState(
            listOf(mock(Episode::class.java)),
            listOf(mock(Channel::class.java)),
            listOf(mock(Category::class.java))
        )
        mutableLiveData.postValue(Resource.loading(state))
        `when`(networkHelper.isConnected()).thenReturn(true)
        testDispatcher.runBlockingTest {
            `when`(categoriesRepository.getCategories()).thenThrow(RuntimeException("Test exception thrown"))
            viewModel.fetchRows()
            Assert.assertEquals(mutableLiveData.value, Resource.error(state))
        }
    }
}