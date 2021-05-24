package com.adademy.discovery.ui.viewholders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adademy.core.R
import com.adademy.discovery.adapter.EpisodesAdapter
import com.adademy.discovery.adapter.VIEW_TYPE_EPISODE
import com.adademy.discovery.adapter.VIEW_TYPE_SERIES
import com.adademy.discovery.databinding.ItemRowChannelBinding
import com.adademy.discovery.model.Channel
import com.adademy.discovery.ui.EpisodesItemDecorator

class RowChannelViewHolder(private val binding: ItemRowChannelBinding): RecyclerView.ViewHolder(binding.root) {
    private val innerAdapter = EpisodesAdapter(mutableListOf(), VIEW_TYPE_EPISODE)
    private val recyclerView: RecyclerView = binding.list

    init {
        recyclerView.adapter = innerAdapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.HORIZONTAL, false)
        recyclerView.addItemDecoration(EpisodesItemDecorator(recyclerView.context.resources.getDimensionPixelSize(R.dimen.margin_xxlarge)))
    }
    fun bind(channel: Channel) {
        binding.channel = channel
        channel.latestMedia?.let {
            if (channel.getChannelType() == Channel.ChannelType.SERIES) {
                innerAdapter.replace(it, VIEW_TYPE_SERIES)
                binding.episodesCount.text = binding.root.context.resources.getString(R.string.series, channel.series?.size?:0)
            } else {
                innerAdapter.replace(it, VIEW_TYPE_EPISODE)
                binding.episodesCount.text = binding.root.context.resources.getQuantityString(R.plurals.episodes, channel.mediaCount, channel.mediaCount)
            }
        }
    }
}