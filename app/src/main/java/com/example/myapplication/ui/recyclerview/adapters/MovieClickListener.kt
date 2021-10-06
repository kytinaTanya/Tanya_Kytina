package com.example.myapplication.ui.recyclerview.adapters

interface MovieClickListener {
    fun onOpenMovie(id: Long)
    fun onOpenTV(id: Long)
    fun onOpenPerson(id: Long)
}