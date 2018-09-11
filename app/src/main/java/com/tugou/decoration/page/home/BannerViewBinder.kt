package com.tugou.decoration.page.home

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.tugou.decoration.R
import com.tugou.decoration.base.SimpleViewHolder
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.home.entity.HomeBannerModel
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

class BannerViewBinder(
        delegate: BannerDelegate
) : TGItemViewBinder<HomeBannerModel, SimpleViewHolder>(), BannerDelegate by delegate {

    override val itemViewRes = R.layout.item_decor_banner

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): SimpleViewHolder {
        return SimpleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, item: HomeBannerModel) {
        with(holder.itemView as ImageView) {
            load(item.image) { simpleOption() }
            setOnClickListener { tapBanner(item) }
        }
    }
}

interface BannerDelegate {
    /**
     * 点击 Banner
     */
    fun tapBanner(banner: HomeBannerModel)
}