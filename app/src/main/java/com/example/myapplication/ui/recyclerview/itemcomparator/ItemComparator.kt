package com.example.myapplication.ui.recyclerview.itemcomparator

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.Person
import com.example.myapplication.models.pojo.TV

object ItemComparator : DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return when (oldItem) {
            is Film -> oldItem.id == (newItem as Film).id
            is TV -> oldItem.id == (newItem as TV).id
            is Person -> oldItem.id == (newItem as Person).id
            else -> oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem == newItem
    }

}