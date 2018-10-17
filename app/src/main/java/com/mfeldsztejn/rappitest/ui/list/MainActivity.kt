package com.mfeldsztejn.rappitest.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mfeldsztejn.rappitest.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(toolbar)

        viewPager.adapter = MainActivityPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2

        tabs.setupWithViewPager(viewPager)
    }

}
