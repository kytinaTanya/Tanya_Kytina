package com.example.myapplication.utils

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
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