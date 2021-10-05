package com.example.myapplication.ui.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MoviesCollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment = ListFragment()
        fragment.arguments = Bundle().apply {
            putInt("object", position + 1)
        }
        return fragment
    }
}