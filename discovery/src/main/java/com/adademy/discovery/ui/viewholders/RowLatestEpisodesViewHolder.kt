package com.adademy.discovery.ui.viewholders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adademy.core.R
import com.adademy.discovery.adapter.EpisodesAdapter
import com.adademy.discovery.adapter.VIEW_TYPE_EPISODE
import com.adademy.discovery.databinding.ItemRowLatestEpisodesBinding
import com.adademy.discovery.model.Episode
import com.adademy.discovery.ui.EpisodesItemDecorator

class RowLatestEpisodesViewHolder(binding: ItemRowLatestEpisodesBinding): RecyclerView.ViewHolder(binding.root) {
    private val innerAdapter = EpisodesAdapter(mutableListOf(), VIEW_TYPE_EPISODE)
    private val recyclerView: RecyclerView = binding.list

    init {
        recyclerView.adapter = innerAdapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.HORIZONTAL, false)
        recyclerView.addItemDecoration(EpisodesItemDecorator(recyclerView.context.resources.getDimensionPixelSize(R.dimen.margin_xxlarge)))
    }
    fun bind(episodes: List<Episode>) {
        innerAdapter.replace(episodes, VIEW_TYPE_EPISODE)
    }
}