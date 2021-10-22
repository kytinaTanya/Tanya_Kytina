package com.example.myapplication.ui.recyclerview.adapters

import com.example.myapplication.ui.MainActivity

interface MovieClickListener {
    fun onOpenMovie(id: Long) {
        println("Open movie")
    }
    fun onOpenTV(id: Long) {
        println("Open TV")
    }
    fun onOpenPerson(id: Long) {
        println("Open person")
    }
    fun onOpenEpisode(tvId: Long, season: Int, episode: Int) {
        println("Open episode")
    }
}