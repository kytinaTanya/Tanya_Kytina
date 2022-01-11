package com.example.myapplication.utils

import android.view.View
import android.content.Context
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.recyclerview.DividerItemDecoration
import com.example.myapplication.ui.recyclerview.adapters.CollectionRecyclerAdapter
import com.squareup.picasso.Picasso

fun ImageView.setImage(imageUrl: String){
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.poster_placeholder)
        .error(R.drawable.poster_placeholder)
        .into(this)
}

fun EditText.getStringText(): String {
    return this.text.toString()
}

fun ImageButton.setCurrentResource(condition: () -> Boolean, @DrawableRes whenConditionRight: Int, @DrawableRes whenConditionWrong: Int) {
    if(condition.invoke()) {
        setBackgroundResource(whenConditionRight)
    } else {
        setBackgroundResource(whenConditionWrong)
    }
}

class Utils {
    companion object {
        fun setIfIsNotEmpty(text: String, view: TextView) {
            if (text == "") {
                view.visibility = View.GONE
            } else {
                view.text = text
            }
        }
    }
}

fun RecyclerView.setConfigHorizontalLinearWithDiv(mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
                                                  context: Context,
                                                  div: Int) {
    this.apply {
        layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,
            false)
        adapter = mAdapter
        addItemDecoration(DividerItemDecoration(div))
    }
}

fun RecyclerView.setConfigVerticalLinear(
    mAdapter: CollectionRecyclerAdapter,
    context: Context) {
    this.apply {
        layoutManager = LinearLayoutManager(context)
        adapter = mAdapter
    }
}