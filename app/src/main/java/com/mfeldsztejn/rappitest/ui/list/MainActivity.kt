package com.mfeldsztejn.rappitest.ui.list

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.mfeldsztejn.rappitest.R
import com.mfeldsztejn.rappitest.events.StartIntentEvent
import kotlinx.android.synthetic.main.main_activity.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerAdapter: MainActivityPagerAdapter
    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(toolbar)

        viewPagerAdapter = MainActivityPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 2
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                searchView!!.isIconified = true
                searchView!!.isIconified = true
                viewPagerAdapter.getFragment(position - 1)?.clearSearch()
                viewPagerAdapter.getFragment(position + 1)?.clearSearch()
            }
        })

        tabs.setupWithViewPager(viewPager)

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)

        val strongMenu = menu!!

        val menuItem = strongMenu.findItem(R.id.action_search)
        searchView = menuItem.actionView as SearchView

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewPagerAdapter
                        .getFragment(viewPager.currentItem)!!
                        .search(query)
                searchView!!.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }


    @Subscribe
    fun onStartIntentEvent(event: StartIntentEvent) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                *event.sharedView.map { Pair.create(it, ViewCompat.getTransitionName(it)) }.toTypedArray())
        startActivity(event.intent, options.toBundle())
    }
}
