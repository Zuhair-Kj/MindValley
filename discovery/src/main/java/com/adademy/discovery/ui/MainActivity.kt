package com.adademy.discovery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.adademy.core.util.Resource
import com.adademy.discovery.R
import com.adademy.discovery.adapter.DiscoveryAdapter
import com.adademy.discovery.databinding.ActivityMainBinding
import com.adademy.discovery.model.CategoryList
import com.adademy.discovery.model.LatestEpisodesList
import com.adademy.discovery.viewmodel.DiscoveryViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: DiscoveryViewModel by viewModel()

    private val adapter = DiscoveryAdapter(mutableListOf())
    lateinit var binding: ActivityMainBinding
    private val observer: Observer<Resource<DiscoveryViewModel.DiscoveryState>> = Observer{ resource ->
        when (resource.status) {
            Resource.Status.ERROR -> {
                binding.recyclerView.visibility = View.GONE
                binding.lottieLoader.visibility = View.GONE
                binding.offlineGroup.visibility = View.VISIBLE
                binding.swiperefresh.isRefreshing = false
            }

            Resource.Status.SUCCESS -> {
                binding.lottieLoader.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                val state = resource.data
                Log.d("ZZZ::State",
                        String.format("categories = %d \n newEpisodes = %d \n channels = %d",
                                state?.categories?.size?: 0,
                                state?.newEpisodes?.size?: 0,
                                state?.channels?.size?:0)
                )
                binding.swiperefresh.isRefreshing = false
                offlineSnackBar.dismiss()
                populateRows(state)
            }

            Resource.Status.NETWORK_DISCONNECTED -> {
                binding.lottieLoader.visibility = View.GONE
                binding.swiperefresh.isRefreshing = false
                if (resource.data?.hasNoRows() != false) {
                    binding.recyclerView.visibility = View.GONE
                    binding.offlineGroup.visibility = View.VISIBLE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                    offlineSnackBar.show()
                    populateRows(resource.data)
                }
            }

            Resource.Status.LOADING -> {
                binding.offlineGroup.visibility = View.GONE
                if (resource.data?.hasNoRows() != false) {
                    binding.recyclerView.visibility = View.GONE
                    binding.offlineGroup.visibility = View.GONE
                    binding.lottieLoader.visibility = View.VISIBLE
                }

//                if (offlineSnackBar.isShown)
//                    offlineSnackBar.dismiss()
//                binding.recyclerView.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val offlineSnackBar by lazy {
        Snackbar.make(binding.coordinatorLayout, R.string.offline, Snackbar.LENGTH_INDEFINITE)
            .also {
                it.view.setBackgroundColor(
                    ContextCompat.getColor(
                        it.context,
                        R.color.purple_500
                    )
                )
            }
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        binding.swiperefresh.post {
            viewModel.propagateLoadingState()
            fetchRows()
            binding.swiperefresh.isRefreshing = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        binding.buttonRetry.setOnClickListener {
            viewModel.propagateLoadingState()
            fetchRows()
        }

        binding.swiperefresh.setOnRefreshListener(refreshListener)
        binding.swiperefresh.setColorSchemeResources(R.color.design_default_color_primary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark)
        viewModel.stateLiveData.observe(this, observer)
        viewModel.stateLiveData.value?.data.let {
            if (it?.hasNoRows() != false) {
                fetchRows()
            }
        }
    }

    private fun fetchRows() {
        viewModel.fetchLatestEpisodes()
        viewModel.fetchChannels()
        viewModel.fetchCategories()
    }

    private fun populateRows(state: DiscoveryViewModel.DiscoveryState?) {
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
}