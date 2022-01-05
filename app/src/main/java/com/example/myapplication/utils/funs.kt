package com.example.myapplication.utils

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.example.myapplication.R
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

fun ImageButton.setCurrentResource(condition: () -> Boolean, @DrawableRes whenConditionRight: Int, @DrawableRes whenConditionWrong: Int) {
    if(condition.invoke()) {
        setBackgroundResource(whenConditionRight)
    } else {
        setBackgroundResource(whenConditionWrong)
    }
}

class Utils {
    companion object {
        fun setIfIsNotEmpty(text: String, view: TextView) {
            if(text == "") {
                view.visibility = View.GONE
            } else {
                view.text = text
            }
        }
    }
}