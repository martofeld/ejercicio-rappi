package com.mfeldsztejn.rappitest.ui.detail

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mfeldsztejn.rappitest.R

class HeaderViewPagerAdapter(private val data: List<ViewPagerAdapterType>) : PagerAdapter(), ViewPager.OnPageChangeListener {

    val sparseArray = SparseArray<View>(data.size)

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = data.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = if (data[position].type == ViewPagerAdapterType.VIDEO) {
            instantiateVideo(container, data[position].url)
        } else {
            instantiateImage(container, data[position].url)
        } as View
        sparseArray.append(position, view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(sparseArray[position])
    }

    private fun instantiateImage(container: ViewGroup, url: String): Any {
        val imageView = LayoutInflater.from(container.context).inflate(R.layout.image_view_layout, container, false) as ImageView
        Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView)
        container.addView(imageView)
        return imageView
    }

    private fun instantiateVideo(container: ViewGroup, url: String): Any {
        val webView = WebView(container.context)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
        container.addView(webView)
        return webView
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (sparseArray[position] is WebView) {
            (sparseArray[position] as WebView).onResume()
        }
        // Pause previous
        if (sparseArray[position - 1] is WebView) {
            (sparseArray[position - 1] as WebView).onPause()
        }
        // Pause next
        if (sparseArray[position + 1] is WebView) {
            (sparseArray[position + 1] as WebView).onPause()
        }
    }
}