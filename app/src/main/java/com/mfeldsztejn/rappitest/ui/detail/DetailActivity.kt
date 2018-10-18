package com.mfeldsztejn.rappitest.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mfeldsztejn.rappitest.MainApplication
import com.mfeldsztejn.rappitest.R
import kotlinx.android.synthetic.main.detail_activity.*
import java.text.SimpleDateFormat


class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        val movieId = intent.data!!.getQueryParameter("id")!!.toLong() // If id is not present fail as fast as possible

        val viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.setMovieId(movieId)

        if (!intent.data!!.getQueryParameter("title").isNullOrEmpty()) {
            movieTitle.text = intent.data!!.getQueryParameter("title")!!
            ViewCompat.setTransitionName(movieTitle, "${movieId}_title")
        }

        ViewCompat.setTransitionName(viewPagerHeader, "${movieId}_image")

        viewModel.movieLiveData.observe(this, Observer { movieDetail ->
            val data = ArrayList<ViewPagerAdapterType>()
            if (movieDetail.backdropPath != null) {
                data.add(
                        ViewPagerAdapterType(ViewPagerAdapterType.IMAGE, MainApplication.configuration.value!!.images.backdropUrl(movieDetail.backdropPath, container.width))
                )
            }
            movieDetail.videos?.results?.forEach {
                data.add(
                        ViewPagerAdapterType(ViewPagerAdapterType.VIDEO, "http://www.youtube.com/embed/${it.key}?autoplay=1&vq=large")
                )
            }

            val adapter = HeaderViewPagerAdapter(data)
            viewPagerHeader.adapter = adapter
            viewPagerHeader.addOnPageChangeListener(adapter)

            movieTitle.text = movieDetail.title
            movieOverview.text = movieDetail.overview
            if (movieDetail.tagline.isNullOrEmpty()) {
                movieTagline.visibility = View.GONE
            }
            movieTagline.text = movieDetail.tagline

            val values = arrayListOf<Pair<String, String>>(
                    Pair(getString(R.string.status), movieDetail.status),
                    Pair(getString(R.string.votes_avg), getString(R.string.votes_avg_value, movieDetail.voteAverage, movieDetail.voteCount)))

            if (movieDetail.releaseDate != null) {
                values.add(0, Pair(getString(R.string.release_date), SimpleDateFormat("dd/MM/yyyy").format(movieDetail.releaseDate)))
            }
            movieExtras.adapter = MovieExtrasAdapter(values)
            movieExtras.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

            movieReviews.adapter = MovieReviewsAdapter(movieDetail.reviews.results)
            movieReviews.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

            movieGenres.adapter = MovieGenresAdapter(movieDetail.genres)
            movieGenres.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
