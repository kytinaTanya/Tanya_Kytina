package com.example.myapplication.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.recyclerview.HorizontalItemsDividerDecoration
import com.example.myapplication.ui.recyclerview.RegularDividerItemDecoration
import com.example.myapplication.ui.recyclerview.VerticalItemsDividerDecoration
import com.squareup.picasso.Picasso

fun ImageView.setImage(imageUrl: String) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.loading_placeholder)
        .error(R.drawable.error_placeholder)
        .into(this)
}

fun EditText.getStringText(): String {
    return this.text.toString()
}

fun ImageButton.setCurrentResource(
    condition: () -> Boolean,
    @DrawableRes whenConditionRight: Int,
    @DrawableRes whenConditionWrong: Int,
) {
    if (condition.invoke()) {
        setBackgroundResource(whenConditionRight)
    } else {
        setBackgroundResource(whenConditionWrong)
    }
}

fun View.showAnimated() {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(200L)
        .setListener(null)
}

fun View.hideAnimated() {
    animate()
        .alpha(0f)
        .setDuration(200L)
        .setListener(null)
    visibility = View.GONE
}

class Utils {
    companion object {
        fun setIfIsNotEmpty(text: String?, view: TextView) {
            if (text == "" || text == null) {
                view.visibility = View.GONE
            } else {
                view.text = text
            }
        }

        fun formatDate(date: String?) : String? {
            if (date == null) return  null
            val year = date.substringBefore("-")
            val month = when (date.substringAfter("-").substringBefore("-").toInt()) {
                1 -> "января"
                2 -> "февраля"
                3 -> "марта"
                4 -> "апреля"
                5 -> "мая"
                6 -> "июня"
                7 -> "июля"
                8 -> "августа"
                9 -> "сентября"
                10 -> "октября"
                11 -> "ноября"
                else -> "декабря"
            }
            val day = date.substringAfterLast("-")
            return "$day $month $year"
        }
    }
}

fun RecyclerView.setConfigHorizontalLinearWithDiv(
    mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    context: Context,
    div: Int,
) {
    mAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    this.apply {
        layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,
            false)
        adapter = mAdapter
        addItemDecoration(RegularDividerItemDecoration(div))
    }
}

fun RecyclerView.setConfigHorizontalWithInnerAndOuterDivs(
    mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    context: Context,
    innerDiv: Int,
    outerDiv: Int
) {
    mAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    this.apply {
        layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,
            false)
        adapter = mAdapter
        addItemDecoration(HorizontalItemsDividerDecoration(innerDivider = innerDiv, outerDivider = outerDiv))
    }
}

fun RecyclerView.setConfigVerticalLinear(
    mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    context: Context,
) {
    mAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    this.apply {
        layoutManager = LinearLayoutManager(context)
        adapter = mAdapter
    }
}

fun RecyclerView.setConfigVerticalWithInnerAndOuterDivs(
    mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    context: Context,
    innerDiv: Int,
    outerDiv: Int
) {
    mAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    this.apply {
        layoutManager = LinearLayoutManager(context)
        adapter = mAdapter
        addItemDecoration(VerticalItemsDividerDecoration(innerDivider = innerDiv, outerDivider = outerDiv))
    }
}