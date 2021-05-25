package com.adademy.discovery.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CategoriesDecorator(private val spanCount: Int, private val sideSpacing: Int, private val verticalSpacing: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val index = parent.indexOfChild(view)

        if (index > parent.childCount - spanCount)
            outRect.bottom = 0
        else {
            outRect.bottom = verticalSpacing
        }
        if (index % spanCount == 0)
            outRect.left = sideSpacing

        outRect.right = sideSpacing
    }
}