package com.adademy.discovery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adademy.discovery.databinding.ItemCategoryBinding
import com.adademy.discovery.model.Category
import com.adademy.discovery.ui.viewholders.CategoryViewHolder

class CategoriesAdapter(private val items: MutableList<Category> = mutableListOf()): RecyclerView.Adapter<CategoryViewHolder>() {

    fun replaceItems(newItems: List<Category>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}