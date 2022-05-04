package com.example.myapplication.ui.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemsDividerDecoration(
    private val innerDivider: Int,
    private val outerDivider: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val adapter = parent.adapter ?: return
        val currentPosition = parent.getChildAdapterPosition(view).takeIf { it != RecyclerView.NO_POSITION } ?: return

        val oneSideInnerDivider = innerDivider / 2

        with(outRect) {
            left = if (currentPosition != 0) oneSideInnerDivider else outerDivider
            right = if (currentPosition != adapter.itemCount - 1) oneSideInnerDivider else outerDivider
        }
    }

}