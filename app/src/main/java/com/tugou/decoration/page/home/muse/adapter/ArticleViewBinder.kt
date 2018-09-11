package com.tugou.decoration.page.home.muse.adapter

import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.decoration.GlideApp
import com.tugou.decoration.R
import com.tugou.decoration.ext.application
import com.tugou.decoration.ext.findView
import com.tugou.decoration.model.muse.entity.MuseModel
import com.tugou.decoration.page.home.muse.ArticleDelegate
import com.tugou.decoration.widget.foundation.TGImageView
import com.tugou.decoration.widget.foundation.TGTextView
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

class ArticleViewBinder(
        delegate: ArticleDelegate
) : TGItemViewBinder<MuseModel.ArticleModel, ArticleViewBinder.ViewHolder>(), ArticleDelegate by delegate {

    private val cardOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val radius = application.dp2px(4f)
            outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, radius)
            outline.alpha = 0.2f
        }
    }

    override val itemViewRes = R.layout.item_muse_article

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView).apply {
            cardArticle.outlineProvider = cardOutlineProvider
            cardArticle.clipToOutline = true
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, article: MuseModel.ArticleModel) {
        with(holder) {
            GlideApp.with(imgAvatar)
                    .load(article.author.avatar)
                    .asAvatar()
                    .into(imgAvatar)
            imgAvatar.setOnClickListener { tapAuthor(article.author) }

            tvUsername.text = article.author.name
            tvUsername.setOnClickListener { tapAuthor(article.author) }

            // 只有未关注的才显示关注按钮
            // 未关注显示推荐原因, 关注则显示发布时间
            if (article.author.isFollowing) {
                btnFollow.visibility = View.GONE
                btnFollow.isSelected = false
                tvTimeOrReason.text = "${article.createTime} · 发布文章"
            } else {
                btnFollow.visibility = View.VISIBLE
                btnFollow.setOnClickListener { tapFollow(article.author) }
                tvTimeOrReason.text = article.recommendReason
            }

            GlideApp.with(imgArticle)
                    .load(article.cover)
                    .simpleOption()
                    .into(imgArticle)

            cardArticle.setOnClickListener { tapArticle(article) }

            // 文章标题
            tvArticleTitle.text = article.title

            // 点赞
            checkLike.isChecked = article.behavior.isLike
            checkLike.text = article.behavior.likeAmount.toString()
            checkLike.setOnClickListener { tapLike(article, !checkLike.isChecked) }
            // 收藏
            checkCollect.isChecked = article.behavior.isCollect
            checkCollect.text = article.behavior.collectAmount.toString()
            checkCollect.setOnClickListener { tapCollect(article, !checkCollect.isChecked) }
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvatar = findView<ImageView>(R.id.imgAvatar)
        val tvUsername = findView<TextView>(R.id.tvUsername)
        val btnFollow = findView<View>(R.id.btnFollow)
        val cardArticle = findView<View>(R.id.museContent)
        val imgArticle = findView<TGImageView>(R.id.imgCover)
        val tvArticleTitle = findView<TGTextView>(R.id.tvArticleTitle)
        val tvTimeOrReason = findView<TextView>(R.id.tvTimeOrReason)
        val checkCollect = findView<AppCompatCheckedTextView>(R.id.checkCollect)
        val checkLike = findView<AppCompatCheckedTextView>(R.id.checkLike)
    }
}