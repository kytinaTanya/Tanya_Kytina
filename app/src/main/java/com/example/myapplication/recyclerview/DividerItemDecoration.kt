package com.example.myapplication.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration (
    private val divider: Int
) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val verticalAndHorizontalDivider = divider / 2

        with(outRect) {
            top = verticalAndHorizontalDivider
            left = verticalAndHorizontalDivider
            bottom = verticalAndHorizontalDivider
            right = verticalAndHorizontalDivider
        }
    }
}