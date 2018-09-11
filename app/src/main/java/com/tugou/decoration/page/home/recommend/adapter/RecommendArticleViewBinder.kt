package com.tugou.decoration.page.home.recommend.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugou.decoration.R
import com.tugou.decoration.ext.clipToCardOutline
import com.tugou.decoration.ext.findView
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.home.entity.RecommendItemModel
import com.tugou.decoration.widget.foundation.TGImageView
import com.tugou.decoration.widget.foundation.TGTextView
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

class RecommendArticleViewBinder(
        delegate: RecommendArticleDelegate
) : TGItemViewBinder<RecommendItemModel.RecommendArticleModel, RecommendArticleViewBinder.ViewHolder>(), RecommendArticleDelegate by delegate {

    override val itemViewRes = R.layout.item_recommend_article

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: RecommendItemModel.RecommendArticleModel) {
        with(holder) {
            imgCover.load(item.cover) { simpleOption() }
            tvArticleTitle.text = item.title
            tvArticleTags.text = item.tags.joinToString(separator = "  #", prefix = "#")
            itemView.setOnClickListener {
                tapArticle(item)
            }
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCover = findView<TGImageView>(R.id.imgCover)
        val tvArticleTitle = findView<TGTextView>(R.id.tvArticleTitle)
        val tvArticleTags = findView<TGTextView>(R.id.tvArticleTags)

        init {
            itemView.clipToCardOutline(4)
        }
    }
}

interface RecommendArticleDelegate {
    /**
     * 点击文章详情
     */
    fun tapArticle(article: RecommendItemModel.RecommendArticleModel)
}