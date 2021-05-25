package com.adademy.discovery.ui.viewholders

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adademy.core.R
import com.adademy.discovery.adapter.CategoriesAdapter
import com.adademy.discovery.databinding.ItemRowCategoriesBinding
import com.adademy.discovery.model.CategoryList
import com.adademy.discovery.ui.CategoriesDecorator

class RowCategoriesViewHolder(private val binding: ItemRowCategoriesBinding): RecyclerView.ViewHolder(binding.root) {
    private val categoriesAdapter = CategoriesAdapter()
    private val list: RecyclerView = binding.list
    private val decorator: CategoriesDecorator

    init {
        val context = binding.root.context
        val spanCount = context.resources.getInteger(R.integer.categories_row_item_count)
        list.layoutManager = GridLayoutManager(context, spanCount)
        list.adapter = categoriesAdapter
        decorator = CategoriesDecorator(spanCount,
                context.resources.getDimensionPixelSize(R.dimen.margin_xxlarge),
                context.resources.getDimensionPixelSize(R.dimen.margin_medium))
        list.addItemDecoration(decorator)

    }

    fun bind(categoriesList: CategoryList) {
        categoriesList.categories?.let {
            categoriesAdapter.replaceItems(it)
        }
    }
}