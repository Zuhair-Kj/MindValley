package com.adademy.discovery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adademy.discovery.R
import com.adademy.discovery.adapter.DiscoveryAdapter
import com.adademy.discovery.databinding.ActivityMainBinding
import com.adademy.discovery.model.CategoryList
import com.adademy.discovery.model.LatestEpisodesList
import com.adademy.discovery.viewmodel.DiscoveryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: DiscoveryViewModel by viewModel()

    private val adapter = DiscoveryAdapter(mutableListOf())
    lateinit var binding: ActivityMainBinding
    private val observer: Observer<DiscoveryViewModel.DiscoveryState> = Observer{ state ->
        Log.d("ZZZ::State",
            String.format("categories = %d \n newEpisodes = %d \n channels = %d",
                state?.categories?.size?: 0,
                state?.newEpisodes?.size?: 0,
                state?.channels?.size?:0)
        )

        val models = mutableListOf<Any>()
        state?.newEpisodes?.let {
            models.addAll(listOf(LatestEpisodesList(it)))
        }

        state?.channels?.let {
            models.addAll(it)
        }

        state?.categories?.let {
            models.add(CategoryList(it))
        }

        adapter.addItems(models)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        viewModel.stateLiveData.observe(this, observer)
        viewModel.stateLiveData.value?.let {
            if (it.newEpisodes?.isNotEmpty() != true && it.categories?.isNotEmpty() != true && it.channels?.isNotEmpty() != true) {
                viewModel.fetchLatestEpisodes()
                viewModel.fetchChannels()
                viewModel.fetchCategories()
            }
        }
    }
}