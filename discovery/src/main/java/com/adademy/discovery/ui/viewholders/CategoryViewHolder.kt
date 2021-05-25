package com.adademy.discovery.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.adademy.discovery.databinding.ItemCategoryBinding
import com.adademy.discovery.model.Category

class CategoryViewHolder(private val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(category: Category) {
        binding.chip.text = category.name
    }
}