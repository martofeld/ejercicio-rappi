package com.mfeldsztejn.rappitest.ui.list

import android.os.Build
import com.mfeldsztejn.rappitest.R
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
// Robolectric has issues with API 28 until the 4.0 release
@Config(sdk = [Build.VERSION_CODES.O_MR1])
internal class MainActivityPagerAdapterTest {

    val activity = Robolectric.setupActivity(MainActivity::class.java)
    val adapter = MainActivityPagerAdapter(activity.supportFragmentManager)

    @Test
    fun `getTitle for different pages, should return correct title`() {
        Assertions.assertThat(activity.getString(R.string.popularity)).isEqualTo(adapter.getPageTitle(0))
        Assertions.assertThat(activity.getString(R.string.top_rated)).isEqualTo(adapter.getPageTitle(1))
        Assertions.assertThat(activity.getString(R.string.upcoming)).isEqualTo(adapter.getPageTitle(2))
    }

    @Test
    fun `getItem should return correct fragment`() {
        var fragment = adapter.getItem(0) as ListFragment
        Assertions.assertThat(fragment.arguments!!.getString("SORT_BY")).isEqualTo("popularity.desc")

        fragment = adapter.getItem(1) as ListFragment
        Assertions.assertThat(fragment.arguments!!.getString("SORT_BY")).isEqualTo("vote_average.desc")

        fragment = adapter.getItem(2) as ListFragment
        Assertions.assertThat(fragment.arguments!!.getString("SORT_BY")).isEqualTo("release_date.desc")
    }

    @Test
    fun `instantiateItem should add it to sparse array and destroy should remove`() {
        Assertions.assertThat(adapter.fragmentMap.size()).isEqualTo(0)

        var item = adapter.instantiateItem(mockk(relaxed = true), 0)
        Assertions.assertThat(adapter.fragmentMap.size()).isEqualTo(1)
        Assertions.assertThat(item).isEqualTo(adapter.fragmentMap.get(0))

        item = adapter.instantiateItem(mockk(relaxed = true), 1)
        Assertions.assertThat(adapter.fragmentMap.size()).isEqualTo(2)
        Assertions.assertThat(item).isEqualTo(adapter.fragmentMap.get(1))

        adapter.destroyItem(mockk(relaxed = true), 1, item)
        Assertions.assertThat(adapter.fragmentMap.size()).isEqualTo(1)
        Assertions.assertThat(adapter.fragmentMap.get(1)).isNull()
    }
}