package com.tugou.decoration.page.home.recommend.adapter

import android.graphics.Outline
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.decoration.R
import com.tugou.decoration.base.SimpleAdapter
import com.tugou.decoration.base.SimpleViewHolder
import com.tugou.decoration.ext.asHorizontalList
import com.tugou.decoration.ext.clipToCardOutline
import com.tugou.decoration.ext.findView
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.home.entity.EntryModel
import com.tugou.decoration.model.home.entity.RecommendItemModel
import com.tugou.decoration.page.home.BannerDelegate
import com.tugou.decoration.page.home.BannerViewBinder
import com.tugou.decoration.widget.foundation.TGTextView
import com.tugou.decoration.widget.recyclerview.SpacingItemDecoration
import com.tugou.decoration.widget.recyclerview.TGAdapter
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder
import me.drakeet.multitype.register

interface DiscoverDelegate : BannerDelegate {
    /**
     * 点击入口
     */
    fun tapEntry(entry: EntryModel)
}

/**
 * 发现兔狗家装 Item
 * Banner + Entry
 */
class DiscoverViewBinder(
        delegate: DiscoverDelegate
) : TGItemViewBinder<RecommendItemModel.DiscoverModel, DiscoverViewBinder.ViewHolder>(), DiscoverDelegate by delegate {

    override val itemViewRes = R.layout.item_discover

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: RecommendItemModel.DiscoverModel) {
        with(holder) {
            tvDiscoverTitle.text = item.title
            recycleDiscoverBanner.adapter = TGAdapter().apply {
                register(BannerViewBinder(this@DiscoverViewBinder))
                setNewData(item.banners)
            }
            recycleDiscoverEntry.adapter = DiscoverEntryAdapter(item.entries)
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDiscoverTitle = findView<TGTextView>(R.id.tvDiscoverTitle)

        val recycleDiscoverBanner = findView<RecyclerView>(R.id.recycleDiscoverBanner).apply {
            asHorizontalList()
            PagerSnapHelper().attachToRecyclerView(this)
            clipToCardOutline(4)
        }

        val recycleDiscoverEntry = findView<RecyclerView>(R.id.recycleDiscoverEntry).apply {
            asHorizontalList()
            GravitySnapHelper(Gravity.END).attachToRecyclerView(this)
            addItemDecoration(SpacingItemDecoration(context.dp2px(6f).toInt(), 0).apply {
                edgeSpacingFactory = 10f / 3f
            })
        }
    }

    inner class DiscoverEntryAdapter(
            entries: List<EntryModel>
    ) : SimpleAdapter<EntryModel>(R.layout.item_discover_entry, entries) {

        private val entryOutlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val radius = view.context.dp2px(4f)
                outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, radius)
                outline.alpha = 0.2f
            }
        }

        override fun onViewHolderCreated(holder: SimpleViewHolder) {
            super.onViewHolderCreated(holder)
            holder.itemView.apply {
                clipToOutline = true
                outlineProvider = entryOutlineProvider
            }
        }

        override fun onBindViewHolder(holder: SimpleViewHolder, item: EntryModel) {
            (holder.itemView as ImageView).apply {
                load(item.icon) { commonOption() }
                setOnClickListener { tapEntry(item) }
            }
        }
    }
}