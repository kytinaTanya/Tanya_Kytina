package com.example.myapplication.ui.recyclerview.listeners

interface EpisodeClickListener {
    fun onOpenEpisode(tvId: Long, season: Int, episode: Int)
}