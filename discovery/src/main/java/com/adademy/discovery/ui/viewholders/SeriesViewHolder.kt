package com.adademy.discovery.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.adademy.discovery.databinding.ItemSeriesBinding
import com.adademy.discovery.model.Episode

class SeriesViewHolder(private val binding: ItemSeriesBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(episode: Episode) {
        binding.episode = episode
        binding.executePendingBindings()
    }
}