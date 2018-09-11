package com.tugou.decoration.page.home.recommend.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.tugou.decoration.R
import com.tugou.decoration.base.SimpleViewHolder
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.home.entity.RecommendItemModel
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

interface RecommendAlbumDelegate {
    /**
     * 点击推荐图集
     */
    fun tapAlbum(album: RecommendItemModel.RecommendAlbumModel)
}

class RecommendAlbumViewBinder(
        delegate: RecommendAlbumDelegate
) : TGItemViewBinder<RecommendItemModel.RecommendAlbumModel, SimpleViewHolder>(), RecommendAlbumDelegate by delegate {

    override val itemViewRes = R.layout.item_recommend_album

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): SimpleViewHolder {
        return SimpleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, item: RecommendItemModel.RecommendAlbumModel) {
        with(holder.itemView as ImageView) {
            load(item.cover) { commonOption() }
            setOnClickListener {
                tapAlbum(item)
            }
        }
    }
}