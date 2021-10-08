package com.example.myapplication.utils

import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.myapplication.R
import com.example.myapplication.ui.MainFragment
import com.squareup.picasso.Picasso

fun ImageView.setImage(imageUrl: String){
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.poster_placeholder)
        .error(R.drawable.poster_placeholder)
        .into(this)
}

fun EditText.getStringText(): String {
    return this.text.toString()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, comment: String) {
    supportFragmentManager.commit {
        setReorderingAllowed(true)
        replace(R.id.fragment_container_view, fragment)
        addToBackStack(comment)
    }
}