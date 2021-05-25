package com.adademy.discovery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adademy.core.util.NetworkHelper
import com.adademy.core.util.Resource
import com.adademy.discovery.model.Category
import com.adademy.discovery.model.Channel
import com.adademy.discovery.model.Episode
import com.adademy.discovery.repository.CategoriesRepository
import com.adademy.discovery.repository.ChannelsRepository
import com.adademy.discovery.repository.LatestEpisodesRepository
import kotlinx.coroutines.*

class DiscoveryViewModel(
    private val channelsRepository: ChannelsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val latestEpisodesRepository: LatestEpisodesRepository,
    private val networkHelper: NetworkHelper,
    private val mutableStateLiveData: MutableLiveData<Resource<DiscoveryState>> = MutableLiveData(Resource.loading())
): ViewModel() {

    val stateLiveData: LiveData<Resource<DiscoveryState>>
    get() = mutableStateLiveData


    fun fetchChannels() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            mutableStateLiveData.postValue(Resource.error(mutableStateLiveData.value?.data))
        }) {
            if (networkHelper.isConnected()) {
                val channels = channelsRepository.getChannels()
                mutableStateLiveData.value?.data.let { oldState ->
                    val state = oldState ?: DiscoveryState()
                    post(Resource.success(state.copy(channels = channels)))
                }
            } else {
                val channels = channelsRepository.getCachedChannels()
                mutableStateLiveData.value?.data.let { oldState ->
                    val state = oldState ?: DiscoveryState()
                    post(Resource.networkError(state.copy(channels = channels)))
                }
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            mutableStateLiveData.postValue(Resource.error(mutableStateLiveData.value?.data))
        }) {
            if (networkHelper.isConnected()) {
                val categories = categoriesRepository.getCategories()
                mutableStateLiveData.value?.data.let { oldState ->
                    val state = oldState ?: DiscoveryState()
                    post(Resource.success(state.copy(categories = categories)))
                }
            } else {
                val categories = categoriesRepository.getCachedCategories()
                mutableStateLiveData.value?.data.let { oldState ->
                    val state = oldState ?: DiscoveryState()
                    post(Resource.networkError(state.copy(categories = categories)))
                }
            }
        }
    }

    fun fetchLatestEpisodes() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            mutableStateLiveData.postValue(Resource.error(mutableStateLiveData.value?.data))
        }) {
            if (networkHelper.isConnected()) {
                val latestEpisodes = latestEpisodesRepository.getLatestEpisodes()
                mutableStateLiveData.value?.data.let { oldState ->
                    val state = oldState ?: DiscoveryState()
                    post(Resource.success(state.copy(newEpisodes = latestEpisodes)))
                }
            } else {
                val latestEpisodes = latestEpisodesRepository.getCachedEpisodes()
                mutableStateLiveData.value?.data.let { oldState ->
                    val state = oldState ?: DiscoveryState()
                    post(Resource.networkError(state.copy(newEpisodes = latestEpisodes)))
                }
            }
        }
    }

    fun propagateLoadingState() {
        post(Resource.loading(mutableStateLiveData.value?.data))
    }

    @Synchronized private fun post(resource: Resource<DiscoveryState>) {
        mutableStateLiveData.postValue(resource)
    }

//    fun fetchDiscoveryRows() {
//        viewModelScope.launch(Dispatchers.IO) {
//            launch {
//
//            }
//
//            launch {
//
//            }
//
//            launch {
//
//            }
//        }
//    }

    data class DiscoveryState(
        var newEpisodes: List<Episode>? = null,
        var channels: List<Channel>? = null,
        var categories: List<Category>? = null
    ) {
        fun hasNoRows(): Boolean {
            return newEpisodes?.isNullOrEmpty() != false
                    && channels?.isNullOrEmpty() != false
                    && categories?.isNullOrEmpty() != false
        }
    }
}