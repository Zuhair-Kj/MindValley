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


    fun fetchRows() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            mutableStateLiveData.postValue(Resource.error(mutableStateLiveData.value?.data))
        }) {
            if (networkHelper.isConnected()) {
                val latestEpisodes = latestEpisodesRepository.getLatestEpisodes()
                val categories = categoriesRepository.getCategories()
                val channels = channelsRepository.getChannels()

                mutableStateLiveData.postValue(Resource.success(DiscoveryState(latestEpisodes, channels, categories)))
            } else {
                val latestEpisodes = latestEpisodesRepository.getCachedEpisodes()
                val categories = categoriesRepository.getCachedCategories()
                val channels = channelsRepository.getCachedChannels()

                mutableStateLiveData.postValue(Resource.networkError(DiscoveryState(latestEpisodes, channels, categories)))
            }
        }
    }

    fun propagateLoadingState() {
        mutableStateLiveData.postValue(Resource.loading(mutableStateLiveData.value?.data))
    }

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