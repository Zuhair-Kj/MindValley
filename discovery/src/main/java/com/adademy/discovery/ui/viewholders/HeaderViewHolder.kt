package com.adademy.discovery.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.adademy.discovery.databinding.ItemRowHeaderBinding

class HeaderViewHolder(private val binding: ItemRowHeaderBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(title: String) {
        binding.title.text = title
    }
}