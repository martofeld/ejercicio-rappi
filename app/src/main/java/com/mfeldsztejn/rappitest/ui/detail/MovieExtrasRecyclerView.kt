package com.mfeldsztejn.rappitest.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mfeldsztejn.rappitest.R

class MovieExtrasAdapter(val data: List<Pair<String, String>>) : RecyclerView.Adapter<MovieExtrasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieExtrasViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.key_value_view_holder, parent, false)
        return MovieExtrasViewHolder(v)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieExtrasViewHolder, position: Int) {
        holder.bind(data[position])
    }

}

class MovieExtrasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<TextView>(R.id.key_value_title)
    private val value = itemView.findViewById<TextView>(R.id.key_value_value)

    fun bind(pair: Pair<String, String>) {
        title.text = pair.first
        value.text = pair.second
    }

}