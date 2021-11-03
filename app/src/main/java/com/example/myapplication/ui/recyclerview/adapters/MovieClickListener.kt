package com.example.myapplication.ui.recyclerview.adapters

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
    fun onOpenSeason(id: Long, season: Int) {
        println("Open season")
    }
    fun onOpenEpisode(tvId: Long, season: Int, episode: Int) {
        println("Open episode")
    }
}