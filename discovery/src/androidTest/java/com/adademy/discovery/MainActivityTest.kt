package com.adademy.discovery

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adademy.core.util.NetworkHelper
import com.adademy.core.util.Resource
import com.adademy.discovery.repository.CategoriesRepository
import com.adademy.discovery.repository.ChannelsRepository
import com.adademy.discovery.repository.LatestEpisodesRepository
import com.adademy.discovery.test.util.UnitTestState
import com.adademy.discovery.ui.MainActivity
import com.adademy.discovery.viewmodel.DiscoveryViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.*
import org.koin.dsl.module
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
class MainActivityTest: KoinTest {
//    @get: Rule
//    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK lateinit var channelsRepository: ChannelsRepository
    @MockK lateinit var categoriesRepository: CategoriesRepository
    @MockK lateinit var latestEpisodesRepositoryTest: LatestEpisodesRepository
    @MockK lateinit var networkHelper: NetworkHelper
    lateinit var mockedViewModel: DiscoveryViewModel
    private val liveData = MutableLiveData<Resource<DiscoveryViewModel.DiscoveryState>>()
    private val unitTestState = UnitTestState<Resource<DiscoveryViewModel.DiscoveryState>>()

    private val testObserver = Observer<Resource<DiscoveryViewModel.DiscoveryState>> {
        unitTestState.obj = it
    }

    private val testModule = module {
        single { DiscoveryViewModel(channelsRepository, categoriesRepository,
            latestEpisodesRepositoryTest, networkHelper, liveData)
        }
    }

    @Before
    fun init() {
        MockKAnnotations.init(this,relaxed = true)
        mockedViewModel = DiscoveryViewModel(channelsRepository, categoriesRepository, latestEpisodesRepositoryTest, networkHelper)
        loadKoinModules(testModule)
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testActivityInView() {
        every {
            networkHelper.isConnected()
        } returns false
        coEvery {
            categoriesRepository.getCachedCategories()
        } returns emptyList()

        coEvery {
            channelsRepository.getCachedChannels()
        } returns emptyList()

        coEvery {
            latestEpisodesRepositoryTest.getCachedEpisodes()
        } returns emptyList()

        liveData.observeForever(testObserver)
        verifySequence {
            unitTestState.obj = Resource.loading()
            unitTestState.obj = Resource.networkError(DiscoveryViewModel.DiscoveryState(emptyList(), emptyList(), emptyList()))
        }
    }

    @After
    fun tearDown() {
        unloadKoinModules(testModule)

        liveData.removeObserver(testObserver)
    }
}