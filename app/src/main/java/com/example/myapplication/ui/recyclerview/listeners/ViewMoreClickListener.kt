package com.example.myapplication.ui.recyclerview.listeners

import com.example.myapplication.viewmodel.MainScreenRequest

interface ViewMoreClickListener {
    fun onOpenMore(requestType: MainScreenRequest)
}