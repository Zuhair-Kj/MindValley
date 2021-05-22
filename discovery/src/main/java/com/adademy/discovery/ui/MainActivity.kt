package com.adademy.discovery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.adademy.discovery.R
import com.adademy.discovery.model.Channel
import com.adademy.discovery.model.Episode
import com.adademy.discovery.model.Category
import com.adademy.discovery.repository.CategoriesRepository
import com.adademy.discovery.repository.ChannelsRepository
import com.adademy.discovery.repository.LatestEpisodesRepository
import com.adademy.discovery.viewmodel.DiscoveryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val channelsRepository: ChannelsRepository by inject()
    private val latestEpisodesRepository: LatestEpisodesRepository by inject()
    private val categoriesRepository: CategoriesRepository by inject()
    private val viewModel: DiscoveryViewModel by viewModel()

    var categories: List<Category>? = null
    var newEpisodes: List<Episode>? = null
    var channels: List<Channel>? = null

    private val observer: Observer<DiscoveryViewModel.DiscoveryState> = Observer{ state ->
        Log.d("ZZZ::State",
            String.format("categories = %d \n newEpisodes = %d \n channels = %d",
                state?.categories?.size?: 0,
                state?.newEpisodes?.size?: 0,
                state?.channels?.size?:0)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.stateLiveData.observe(this, observer)
        viewModel.fetchLatestEpisodes()
        viewModel.fetchChannels()
        viewModel.fetchCategories()
    }
}