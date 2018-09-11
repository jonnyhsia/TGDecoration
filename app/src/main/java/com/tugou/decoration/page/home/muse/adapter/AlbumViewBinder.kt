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
import com.tugou.decoration.page.home.muse.AlbumDelegate
import com.tugou.decoration.widget.foundation.TGImageView
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

internal class AlbumViewBinder(
        delegate: AlbumDelegate
) : TGItemViewBinder<MuseModel.AlbumModel, AlbumViewBinder.ViewHolder>(), AlbumDelegate by delegate {

    override val itemViewRes = R.layout.item_muse_album

    private val cardOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val radius = application.dp2px(4f)
            outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, radius)
            outline.alpha = 0.2f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView).apply {
            imgAlbum.outlineProvider = cardOutlineProvider
            imgAlbum.clipToOutline = true
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, album: MuseModel.AlbumModel) {
        with(holder) {
            GlideApp.with(imgAvatar)
                    .load(album.author.avatar)
                    .asAvatar()
                    .into(imgAvatar)
            imgAvatar.setOnClickListener { tapAuthor(album.author) }

            tvUsername.text = album.author.name
            tvUsername.setOnClickListener { tapAuthor(album.author) }

            // 只有未关注的才显示关注按钮
            // 未关注显示推荐原因, 关注则显示发布时间
            if (album.author.isFollowing) {
                btnFollow.visibility = View.GONE
                btnFollow.isSelected = false
                tvTimeOrReason.text = "${album.createTime} · 发布文章"
            } else {
                btnFollow.visibility = View.VISIBLE
                btnFollow.setOnClickListener { tapFollow(album.author) }
                tvTimeOrReason.text = album.recommendReason
            }

            GlideApp.with(imgAlbum)
                    .load(album.cover)
                    .simpleOption()
                    .into(imgAlbum)

            imgAlbum.setOnClickListener { tapAlbum(album) }

            // 点赞
            checkLike.isChecked = album.behavior.isLike
            checkLike.text = album.behavior.likeAmount.toString()
            checkLike.setOnClickListener { tapLike(album, !checkLike.isChecked) }
            // 收藏
            checkCollect.isChecked = album.behavior.isCollect
            checkCollect.text = album.behavior.collectAmount.toString()
            checkCollect.setOnClickListener { tapCollect(album, !checkCollect.isChecked) }
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvatar = findView<ImageView>(R.id.imgAvatar)
        val tvUsername = findView<TextView>(R.id.tvUsername)
        val btnFollow = findView<View>(R.id.btnFollow)
        val imgAlbum = findView<TGImageView>(R.id.museContent)
        val tvTimeOrReason = findView<TextView>(R.id.tvTimeOrReason)
        val checkCollect = findView<AppCompatCheckedTextView>(R.id.checkCollect)
        val checkLike = findView<AppCompatCheckedTextView>(R.id.checkLike)
    }
}