package com.example.myapplication.view

import com.example.myapplication.movies.Movie

interface MovieView {
    fun showMovies(mList: List<Movie>);
}