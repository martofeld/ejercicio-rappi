package com.mfeldsztejn.rappitest.ui.list

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mfeldsztejn.rappitest.MainApplication
import com.mfeldsztejn.rappitest.R

class MainActivityPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val fragmentMap: SparseArray<Fragment> = SparseArray(3)

    override fun getItem(position: Int): Fragment {
        val sortBy =
                when (position) {
                    1 -> "vote_average.desc"
                    2 -> "release_date.desc"
                    else -> "popularity.desc"
                }
        return ListFragment.newInstance(sortBy)
    }

    override fun getCount() = 3

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fragmentMap.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        fragmentMap.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            1 -> MainApplication.application.getString(R.string.top_rated)
            2 -> MainApplication.application.getString(R.string.upcoming)
            else -> MainApplication.application.getString(R.string.popularity)
        }
    }
}