package com.mfeldsztejn.rappitest.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import com.mfeldsztejn.rappitest.R
import com.mfeldsztejn.rappitest.events.StartIntentEvent
import kotlinx.android.synthetic.main.main_activity.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(toolbar)

        viewPager.adapter = MainActivityPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2

        tabs.setupWithViewPager(viewPager)

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onStartIntentEvent(event: StartIntentEvent) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                *event.sharedView.map { Pair.create(it, ViewCompat.getTransitionName(it)) }.toTypedArray())
        startActivity(event.intent, options.toBundle())
    }
}
