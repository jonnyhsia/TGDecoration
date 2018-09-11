package com.tugou.decoration.page.home.recommend.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.tugou.decoration.model.home.entity.MagazineModel
import com.tugou.decoration.model.home.entity.RecommendItemModel
import com.tugou.decoration.widget.foundation.TGTextView
import com.tugou.decoration.widget.recyclerview.SpacingItemDecoration
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

interface MagazinesDelegate {
    /**
     * 点击杂志
     */
    fun tapMagazine(magazineModel: MagazineModel)
}

class MagazinesViewBinder(
        delegate: MagazinesDelegate
) : TGItemViewBinder<RecommendItemModel.MagazineListModel, MagazinesViewBinder.ViewHolder>(), MagazinesDelegate by delegate {

    override val itemViewRes = R.layout.item_magazines

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: RecommendItemModel.MagazineListModel) {
        with(holder) {
            tvMagazineTitle.text = item.title
            recycleMagazine.adapter = MagazineAdapter(item.magazines)
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMagazineTitle = findView<TGTextView>(R.id.tvMagazineTitle)
        val recycleMagazine = findView<RecyclerView>(R.id.recycleMagazine).apply {
            asHorizontalList()
            GravitySnapHelper(Gravity.START).attachToRecyclerView(this)
            addItemDecoration(SpacingItemDecoration(0, 0, context.dp2px(12f).toInt(), 0).apply {
                edgeSpacingFactory = 0f
            })
        }
    }

    inner class MagazineAdapter(magazines: List<MagazineModel>) : SimpleAdapter<MagazineModel>(R.layout.item_magazine, magazines) {

        override fun onViewHolderCreated(holder: SimpleViewHolder) {
            super.onViewHolderCreated(holder)
            holder.itemView.apply {
                clipToCardOutline(4)
            }
        }

        override fun onBindViewHolder(holder: SimpleViewHolder, item: MagazineModel) {
            (holder.itemView as ImageView).apply {
                load(item.cover) { commonOption() }
                setOnClickListener { tapMagazine(item) }
            }
        }
    }
}