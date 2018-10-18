package com.mfeldsztejn.rappitest.ui.list

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mfeldsztejn.rappitest.MainApplication
import com.mfeldsztejn.rappitest.R
import com.mfeldsztejn.rappitest.dtos.Configuration
import com.mfeldsztejn.rappitest.dtos.Movie
import com.mfeldsztejn.rappitest.events.ConfigurationObtainedEvent
import com.mfeldsztejn.rappitest.events.StartIntentEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MoviesAdapter : PagedListAdapter<Movie, MovieViewHolder>(MoviesItemCallback()) {
    private var screenSize: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MovieViewHolder {
        screenSize = parent.width
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater.inflate(R.layout.movie_view_holder, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position)!!, screenSize)
    }
}

class MoviesItemCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}

class MovieViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.movie_title)
    private val image: ImageView = itemView.findViewById(R.id.movie_image)
    private var screenSize: Int = 0
    private var movie: Movie? = null

    fun bind(movie: Movie, screenSize: Int) {
        this.movie = movie
        this.screenSize = screenSize

        title.text = movie.title

        ViewCompat.setTransitionName(image, "${movie.id}_image")
        ViewCompat.setTransitionName(title, "${movie.id}_title")

        if (MainApplication.configuration.value == null) {
            MainApplication.configuration.observe(image.context as LifecycleOwner, Observer {
                loadImage(it)
            })
        } else {
            loadImage(MainApplication.configuration.value!!)
        }

        itemView.setOnClickListener {
            val uri = Uri.parse("rappitest://detail?id=${movie.id}&title=${movie.title}")
            EventBus.getDefault().post(StartIntentEvent(Intent(Intent.ACTION_VIEW, uri), listOf(image, title)))
        }
    }

    private fun loadImage(configuration: Configuration) {
        // Might be null if the event is triggered first
        if (movie == null) {
            return
        }
        val with = Glide.with(image.context)
        val requestBuilder: RequestBuilder<Drawable> =
                if (movie!!.backdropPath == null) {
                    with.load(R.drawable.ic_error)
                } else {
                    with.load(configuration.images.backdropUrl(movie!!.backdropPath!!, screenSize))
                            .apply(RequestOptions
                                    .centerCropTransform()
                                    .error(R.drawable.ic_error)
                                    .placeholder(R.color.black)
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                }
        requestBuilder
                .into(image)
    }

    @Subscribe
    fun configurationObtainedEvent(event: ConfigurationObtainedEvent) {
        loadImage(event.configuration)
    }
}