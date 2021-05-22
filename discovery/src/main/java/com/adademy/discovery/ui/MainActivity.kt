package com.adademy.discovery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.adademy.discovery.R
import com.adademy.discovery.model.CategoriesResponse
import com.adademy.discovery.model.ChannelsResponse
import com.adademy.discovery.model.LatestEpisodesRespone
import com.adademy.discovery.network.BrowseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val service: BrowseService by inject()

    lateinit var categoriesResponse: CategoriesResponse
    lateinit var newEpisodesResponse: LatestEpisodesRespone
    lateinit var channelsResponse: ChannelsResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch(Dispatchers.IO) {
            categoriesResponse = service.getCategories()
            newEpisodesResponse = service.getLatestEpisodes()
            channelsResponse = service.getChannels()
        }
    }
}