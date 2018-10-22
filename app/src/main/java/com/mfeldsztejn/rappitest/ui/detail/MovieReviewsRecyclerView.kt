package com.mfeldsztejn.rappitest.ui.detail

import android.animation.LayoutTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mfeldsztejn.rappitest.R
import com.mfeldsztejn.rappitest.dtos.Review

class MovieReviewsAdapter(val data: List<Review>) : RecyclerView.Adapter<MovieReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.review_view_holder, parent, false)
        return MovieReviewViewHolder(itemView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieReviewViewHolder, position: Int) {
        holder.bind(data[position])
    }

}

class MovieReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val author = itemView.findViewById<TextView>(R.id.review_author)
    private val content = itemView.findViewById<TextView>(R.id.review_content)

    fun bind(review: Review) {
        (itemView as LinearLayout).layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        author.text = review.author
        content.text = review.content
        content.maxLines = 4
        content.setOnClickListener {
            val textView = it as TextView
            if (textView.maxLines == Int.MAX_VALUE) {
                textView.maxLines = 4
            } else {
                textView.maxLines = Int.MAX_VALUE
            }
        }
    }
}