package com.example.myapplication.recyclerview

import androidx.recyclerview.selection.ItemKeyProvider
import com.example.myapplication.adapters.MovieRecyclerAdapter

class MovieItemKeyProvider(private val adapter: MovieRecyclerAdapter)
    : ItemKeyProvider<Long>(SCOPE_CACHED) {
    override fun getKey(position: Int): Long? {
        return adapter.mMoviesList[position].mid
    }

    override fun getPosition(key: Long): Int {
        return adapter.mMoviesList.indexOfFirst { it.mid == key }
    }
}