package com.example.myapplication.utils

import android.widget.ImageView
import com.example.myapplication.R
import com.squareup.picasso.Picasso

fun ImageView.setImage(imageUrl: String){
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.poster_placeholder)
        .error(R.drawable.poster_placeholder)
        .into(this)
}