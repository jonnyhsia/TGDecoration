package com.tugou.decoration.page.home.mall

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.core.TGFragment
import com.tugou.core.getViewModel
import com.tugou.core.observe
import com.tugou.decoration.R
import com.tugou.decoration.ext.clipToCardOutline
import com.tugou.decoration.model.muse.entity.MallRecommendItem
import com.tugou.decoration.page.home.BannerViewBinder
import com.tugou.decoration.page.home.EntryViewBinder
import com.tugou.decoration.page.home.mall.adapter.*
import com.tugou.decoration.widget.recyclerview.TGAdapter
import com.tugou.decoration.widget.recyclerview.WareSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_mall.*
import kotlinx.android.synthetic.main.include_home_config.*
import me.drakeet.multitype.register

class MallFragment : TGFragment<MallViewModel>() {
    override val layoutRes = R.layout.fragment_mall

    private val bannerAdapter = TGAdapter()
    private val entryAdapter = TGAdapter()
    private val recommendAdapter = TGAdapter()

    override fun onCreateViewModel(savedInstanceState: Bundle?) {
        viewModel = getViewModel()
        viewModel.fetchMallConfig()
        viewModel.fetchRecommendList()

        bannerAdapter.register(BannerViewBinder(viewModel))
        entryAdapter.register(EntryViewBinder(viewModel))

        recommendAdapter.apply {
            register(WareCollectionViewBinder(viewModel))
            register(ArticleCollectionViewBinder(viewModel))
            register(RecommendHeaderViewBinder())
            register(RecommendWareViewBinder(viewModel))
            register(RecommendSubjectViewBinder(viewModel))
        }

        viewModel.mallConfig.observe(this) {
            val (banners, entries) = it ?: return@observe
            bannerAdapter.setNewData(banners)
            entryAdapter.setNewData(entries)
        }

        viewModel.mallRecommend.observe(this) {
            it ?: return@observe
            recommendAdapter.setNewData(it.list)
        }
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

        recycleMall.apply {
            setHasFixedSize(true)
            val spacing = context.dp2px(5f).toInt()
            layoutManager = GridLayoutManager(context, 2).apply {
                addItemDecoration(WareSpacingItemDecoration(spacing, spacing * 4) {
                    recommendAdapter.items[it] is MallRecommendItem.RecommendWareModel
                })

                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val item = recommendAdapter.items[position]
                        return when (item) {
                            is MallRecommendItem.RecommendWareModel -> 1
                            else -> 2
                        }
                    }
                }
            }
            adapter = recommendAdapter
        }
    }
}