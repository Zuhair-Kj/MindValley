package com.adademy.discovery.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseEpisodeViewHolder<B: ViewBinding, M>(protected val binding: B): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(model: M)
}