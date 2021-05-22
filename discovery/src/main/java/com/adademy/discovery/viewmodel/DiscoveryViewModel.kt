package com.adademy.discovery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adademy.discovery.model.Category
import com.adademy.discovery.model.Channel
import com.adademy.discovery.model.Episode
import com.adademy.discovery.repository.CategoriesRepository
import com.adademy.discovery.repository.ChannelsRepository
import com.adademy.discovery.repository.LatestEpisodesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiscoveryViewModel(
    private val channelsRepository: ChannelsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val latestEpisodesRepository: LatestEpisodesRepository,
    private val mutableStateLiveData: MutableLiveData<DiscoveryState> = MutableLiveData(DiscoveryState())
): ViewModel() {

    val stateLiveData: LiveData<DiscoveryState>
    get() = mutableStateLiveData


    fun fetchChannels() {
        viewModelScope.launch(Dispatchers.IO) {
            val channels = channelsRepository.getChannels()
            mutableStateLiveData.value?.let { state ->
                mutableStateLiveData.postValue(state.copy(channels = channels))
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = categoriesRepository.getCategories()
            mutableStateLiveData.value?.let { state ->
                mutableStateLiveData.postValue(state.copy(categories = categories))
            }
        }
    }

    fun fetchLatestEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
            val latestEpisodes = latestEpisodesRepository.getLatestEpisodes()
            mutableStateLiveData.value?.let { state ->
                mutableStateLiveData.postValue(state.copy(newEpisodes = latestEpisodes))
            }
        }
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
    )
}