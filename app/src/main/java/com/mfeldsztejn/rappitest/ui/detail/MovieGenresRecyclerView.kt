package com.mfeldsztejn.rappitest.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mfeldsztejn.rappitest.R
import com.mfeldsztejn.rappitest.dtos.Genre

class MovieGenresAdapter(val genres: List<Genre>) : RecyclerView.Adapter<MovieGenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_view_holder, parent, false)
        return MovieGenreViewHolder(view)
    }

    override fun getItemCount() = genres.size

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

}

class MovieGenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(genre: Genre) {
        (itemView as TextView).text = genre.name
    }
}