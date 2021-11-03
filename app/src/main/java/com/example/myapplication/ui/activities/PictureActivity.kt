package com.example.myapplication.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPictureBinding
import com.example.myapplication.ui.activities.ItemInfoActivity.Companion.PICTURE_URL
import com.example.myapplication.utils.setImage

class PictureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.picture.setImage(intent.extras?.get(PICTURE_URL).toString() ?: "")
    }
}