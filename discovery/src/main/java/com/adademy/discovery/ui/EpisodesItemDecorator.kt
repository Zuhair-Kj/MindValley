package com.adademy.discovery.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EpisodesItemDecorator(private val spacing: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = spacing
        if (parent.indexOfChild(view) == parent.childCount - 1) {
            outRect.right = spacing
        }
    }
}