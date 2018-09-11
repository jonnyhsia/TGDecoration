package com.tugou.decoration.page.home.muse.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.decoration.R
import com.tugou.decoration.base.SimpleAdapter
import com.tugou.decoration.base.SimpleViewHolder
import com.tugou.decoration.ext.application
import com.tugou.decoration.ext.asGridList
import com.tugou.decoration.ext.findView
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.home.entity.DecorProgressTipsModel
import com.tugou.decoration.model.home.entity.DecorTipModel
import com.tugou.decoration.model.muse.entity.MuseModel
import com.tugou.decoration.page.home.muse.ProgressTipsDelegate
import com.tugou.decoration.widget.design.TextTabLayout
import com.tugou.decoration.widget.recyclerview.SpacingItemDecoration
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

class ProgressTipsViewBinder(
        delegate: ProgressTipsDelegate
) : TGItemViewBinder<MuseModel.TipModel, ProgressTipsViewBinder.ViewHolder>(), ProgressTipsDelegate by delegate {

    override val itemViewRes = R.layout.item_decor_tips

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: MuseModel.TipModel) {
        with(holder) {
            val adapter = ProgressTipsAdapter { tapTip(it) }
            adapter.setProgressTips(item.list)
            pagerDecorProgress.adapter = adapter
            tabTips.setUpWithViewPager(pagerDecorProgress)
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tabTips = findView<TextTabLayout>(R.id.tabProgressTips)
        val pagerDecorProgress = findView<ViewPager>(R.id.pagerProgressTips).apply {
            offscreenPageLimit = 2
        }
    }
}

class ProgressTipsAdapter(
        val tapTip: (DecorTipModel) -> Unit
) : PagerAdapter() {

    private val pagerData = ArrayList<DecorProgressTipsModel>()
    private val views = ArrayList<RecyclerView?>()
    private val tipItemDecoration: SpacingItemDecoration =
            SpacingItemDecoration(application.dp2px(6f).toInt())

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return views[position] ?: createViewByPosition(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(views[position])
    }

    private fun createViewByPosition(container: ViewGroup, position: Int) = RecyclerView(container.context).apply {
        views[position] = this
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        container.addView(this)
        val padding = context.dp2px(14f).toInt()
        setPaddingRelative(padding, 0, padding, 0)
        asGridList(3)
        isNestedScrollingEnabled = false
        adapter = TipAdapter(pagerData[position].tips) { tapTip(it) }
        addItemDecoration(tipItemDecoration)
    }

    fun setProgressTips(data: List<DecorProgressTipsModel>) {
        pagerData.clear()
        pagerData.addAll(data)
        views.clear()
        views.addAll(List<RecyclerView?>(data.size) { null })
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int) = pagerData[position].progress

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getCount() = pagerData.size

    class TipAdapter(
            data: List<DecorTipModel>,
            val tapTip: (DecorTipModel) -> Unit
    ) : SimpleAdapter<DecorTipModel>(R.layout.item_decor_tip, data) {

        override fun onBindViewHolder(holder: SimpleViewHolder, item: DecorTipModel) {
            (holder.itemView as ImageView).apply {
                load(item.image) { commonOption() }
                setOnClickListener {
                    tapTip(item)
                }
            }
        }
    }
}