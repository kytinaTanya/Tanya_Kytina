package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class CustomRecyclerAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleMovie: TextView
        val yearOfMovie: TextView
        val annotationMovie: TextView
        val imageMovie: ImageView

        init {
            titleMovie = view.findViewById(R.id.movieTitle)
            yearOfMovie = view.findViewById(R.id.yearOfMovie)
            annotationMovie = view.findViewById(R.id.movieAnnotation)
            imageMovie = view.findViewById(R.id.movieImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleMovie.text = "Movie № ${dataSet[position]}"
        holder.yearOfMovie.text = (2000 + dataSet[position].toInt()).toString()
        holder.annotationMovie.text = "Movie № ${dataSet[position]} annotation ".repeat(20)
    }

    override fun getItemCount() = dataSet.size
}