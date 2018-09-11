package com.tugou.decoration.page.home.muse

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.core.TGFragment
import com.tugou.core.getViewModel
import com.tugou.decoration.R
import com.tugou.decoration.ext.clipToCardOutline
import com.tugou.decoration.page.home.BannerViewBinder
import com.tugou.decoration.page.home.EntryViewBinder
import com.tugou.decoration.page.home.muse.adapter.*
import com.tugou.decoration.widget.recyclerview.*
import kotlinx.android.synthetic.main.fragment_muse.*
import kotlinx.android.synthetic.main.include_home_config.*
import me.drakeet.multitype.Items
import me.drakeet.multitype.register

class MuseFragment : TGFragment<MuseViewModel>() {
    override val layoutRes = R.layout.fragment_muse

    private val timelineAdapter = TGAdapter()
    private val bannerAdapter = TGAdapter()
    private val entryAdapter = TGAdapter()

    override fun onCreateViewModel(savedInstanceState: Bundle?) {
        viewModel = getViewModel()
        viewModel.fetchConfig()
        viewModel.fetchTimeline()

        timelineAdapter.apply {
            register(ArticleViewBinder(viewModel))
            register(AlbumViewBinder(viewModel))
            register(PictureViewBinder(viewModel))
            register(RecommendUserViewBinder(viewModel))
            register(ProgressTipsViewBinder(viewModel))
        }

        bannerAdapter.register(BannerViewBinder(viewModel))
        entryAdapter.register(EntryViewBinder(viewModel))

        viewModel.museConfig.observe(this, Observer {
            val (banners, entries) = it

            bannerAdapter.setNewData(banners)
            entryAdapter.setNewData(entries)
        })

        viewModel.museTimeline.observe(this, Observer {
            timelineAdapter.items = Items(it)
            timelineAdapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collapsingToolbarLayout.apply {
            setExpandedTitleTypeface(Typeface.DEFAULT_BOLD)
            setCollapsedTitleTypeface(Typeface.DEFAULT_BOLD)
        }

        recycleBanner.apply {
            setHasFixedSize(true)
            PagerSnapHelper().attachToRecyclerView(this)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            clipToCardOutline(6)
            adapter = bannerAdapter
        }

        recycleEntry.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 4)
            adapter = entryAdapter
        }

        recycleMuseTimeline.apply {
            setHasFixedSize(true)
            addItemDecoration(SpacingItemDecoration(0).apply {
                val inset = requireContext().dp2px(20f).toInt()
                dividerInset = inset to inset
                drawDivider = DIVIDER_BEGINNING or DIVIDER_MIDDLE
                divider = ContextCompat.getDrawable(requireContext(), R.drawable.divider)
            })
            layoutManager = ControllableLinearLayoutManager(requireContext(), 80)
            adapter = timelineAdapter
        }
    }
}