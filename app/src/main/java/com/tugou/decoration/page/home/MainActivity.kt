package com.tugou.decoration.page.home

import android.os.Bundle
import androidx.fragment.app.transaction
import com.tugou.core.Corgi
import com.tugou.core.TGActivity
import com.tugou.decoration.R
import com.tugou.decoration.page.home.mall.MallFragment
import com.tugou.decoration.page.home.mine.MineFragment
import com.tugou.decoration.page.home.muse.MuseFragment
import com.tugou.decoration.page.home.recommend.RecommendFragment
import com.tugou.decoration.widget.design.NavigationItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : TGActivity(), Corgi {

    override val layoutRes = R.layout.activity_main

    private val fragmentTags = listOf("discover", "muse", "mall", "mine")
    private val fragmentInitializer = listOf(
            { RecommendFragment() },
            { MuseFragment() },
            { MallFragment() },
            { MineFragment() }
    )

    override fun onContentCreated(savedInstanceState: Bundle?) {
        bottomNavigation.apply {
            navigationListener = ::navigateHomeFragment
            setNavigationItems(listOf(
                    NavigationItem(R.drawable.radio_recommend, R.string.nav_recommend),
                    NavigationItem(R.drawable.radio_muse, R.string.nav_muse),
                    NavigationItem(R.drawable.radio_mall, R.string.nav_mall),
                    NavigationItem(R.drawable.radio_mine, R.string.nav_mine)
            ))
            if (savedInstanceState == null) {
                performSelected(0)
            }
        }
    }

    private fun navigateHomeFragment(last: Int, position: Int) {
        val fragmentToHide = if (last >= 0) supportFragmentManager.findFragmentByTag(fragmentTags[last]) else null
        val fragmentToShow = supportFragmentManager.findFragmentByTag(fragmentTags[position])

        supportFragmentManager.transaction {
            if (fragmentToShow == null) {
                add(R.id.container, fragmentInitializer[position](), fragmentTags[position])
            } else {
                show(fragmentToShow)
            }
            fragmentToHide?.let { hide(it) }
        }
    }
}