package com.adademy.discovery.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.adademy.discovery.R
import com.adademy.discovery.databinding.ItemEpisodeBinding
import com.adademy.discovery.model.Episode

class EpisodeViewHolder(private val binding: ItemEpisodeBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Episode) {
        binding.episode = model
        binding.executePendingBindings()
    }
}