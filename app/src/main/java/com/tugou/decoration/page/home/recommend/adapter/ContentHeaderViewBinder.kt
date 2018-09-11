package com.tugou.decoration.page.home.recommend.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.decoration.R
import com.tugou.decoration.base.SimpleAdapter
import com.tugou.decoration.base.SimpleViewHolder
import com.tugou.decoration.ext.asHorizontalList
import com.tugou.decoration.ext.findView
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.home.entity.RecommendItemModel
import com.tugou.decoration.model.home.entity.TagModel
import com.tugou.decoration.widget.foundation.TGTextView
import com.tugou.decoration.widget.recyclerview.BounceRecyclerView
import com.tugou.decoration.widget.recyclerview.SpacingItemDecoration
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

interface HeaderDelegate {
    /**
     * 点击推荐标签
     */
    fun tapRecommendTag(tag: TagModel)
}

/**
 * 内容的头部
 * 标题 + 推荐标签
 */
class ContentHeaderViewBinder(
        delegate: HeaderDelegate
) : TGItemViewBinder<RecommendItemModel.ContentHeaderModel, ContentHeaderViewBinder.ViewHolder>(), HeaderDelegate by delegate {

    override val itemViewRes = R.layout.item_content_header

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: RecommendItemModel.ContentHeaderModel) {
        with(holder) {
            tvHeaderTitle.text = item.title
            recycleRecommendTags.adapter = TagAdapter(item.tags)
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeaderTitle = findView<TGTextView>(R.id.tvHeaderTitle)
        val recycleRecommendTags = findView<BounceRecyclerView>(R.id.recycleRecommendTags).apply {
            asHorizontalList()
            addItemDecoration(SpacingItemDecoration(0, 0, context.dp2px(12f).toInt(), 0).apply {
                edgeSpacingFactory = 0f
            })
        }
    }

    inner class TagAdapter(tags: List<TagModel>) : SimpleAdapter<TagModel>(R.layout.item_recommend_tag, tags) {

        override fun onBindViewHolder(holder: SimpleViewHolder, item: TagModel) {
            with(holder.itemView as ImageView) {
                load(item.image) { commonOption() }
                setOnClickListener {
                    tapRecommendTag(item)
                }
            }
        }
    }
}
