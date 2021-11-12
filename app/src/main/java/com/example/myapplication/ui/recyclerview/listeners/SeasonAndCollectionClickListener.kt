package com.example.myapplication.ui.recyclerview.listeners

interface SeasonAndCollectionClickListener {
    fun onOpenSeason(id: Long, season: Int)
    fun onOpenCollection(id: Int)
}