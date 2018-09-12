package com.tugou.decoration.page.home.muse.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.tugou.andromeda.kit.ui.displayWidth
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.decoration.R
import com.tugou.decoration.ext.application
import com.tugou.decoration.ext.context
import com.tugou.decoration.ext.findView
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.muse.entity.MuseModel
import com.tugou.decoration.page.home.muse.SinglePictureDelegate
import com.tugou.decoration.widget.image.RoundCorner
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

class PictureViewBinder(
        delegate: SinglePictureDelegate
) : TGItemViewBinder<MuseModel.SinglePictureModel, PictureViewBinder.ViewHolder>(), SinglePictureDelegate by delegate {

    private val radius = application.dp2px(4f)

    override val itemViewRes = R.layout.item_muse_picture

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, picture: MuseModel.SinglePictureModel) {
        with(holder) {
            imgAvatar.load(picture.author.avatar) { asAvatar() }
            imgAvatar.setOnClickListener { tapAuthor(picture.author) }

            tvUsername.text = picture.author.name
            tvUsername.setOnClickListener { tapAuthor(picture.author) }

            // 只有未关注的才显示关注按钮
            // 未关注显示推荐原因, 关注则显示发布时间
            if (picture.author.isFollowing) {
                btnFollow.visibility = View.GONE
                btnFollow.isSelected = false
                tvTimeOrReason.text = "${picture.createTime} · 发布文章"
            } else {
                btnFollow.visibility = View.VISIBLE
                btnFollow.setOnClickListener { tapFollow(picture.author) }
                tvTimeOrReason.text = picture.recommendReason
            }

            val width = context.displayWidth - context.dp2px(42f)
            imgSinglePicture.layoutParams?.height = (width / picture.aspectRatio).toInt()
            imgSinglePicture.load(picture.image) {
                diskCacheStrategy(DiskCacheStrategy.ALL)
                transforms(CenterCrop(), RoundCorner(radius, radius, 0f, 0f))
                placeholder(R.drawable.placeholder_rounded_gray)
            }

            tvPictureDescription.text = picture.description
            tvPictureDescription.post {
                if (tvPictureDescription.lineCount > 4) {
                    tvPictureDescription.maxLines = 4
                    tvSeeMore.visibility = View.VISIBLE
                } else {
                    tvPictureDescription.maxLines = Int.MAX_VALUE
                    tvSeeMore.visibility = View.GONE
                }
            }

            tvSeeMore.setOnClickListener {
                tvPictureDescription.maxLines = Int.MAX_VALUE
                tvSeeMore.visibility = View.GONE
            }

            museContent.setOnClickListener { tapPicture(picture) }

            // 点赞
            checkLike.isChecked = picture.behavior.isLike
            checkLike.text = picture.behavior.likeAmount.toString()
            checkLike.setOnClickListener { tapLike(picture, !checkLike.isChecked) }
            // 收藏
            checkCollect.isChecked = picture.behavior.isCollect
            checkCollect.text = picture.behavior.collectAmount.toString()
            checkCollect.setOnClickListener { tapCollect(picture, !checkCollect.isChecked) }
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val imgAvatar = findView<ImageView>(R.id.imgAvatar)
        internal val tvUsername = findView<TextView>(R.id.tvUsername)
        internal val btnFollow = findView<TextView>(R.id.btnFollow)
        internal val museContent = findView<View>(R.id.museContent)
        internal val tvTimeOrReason = findView<TextView>(R.id.tvTimeOrReason)
        internal val checkCollect = findView<CheckedTextView>(R.id.checkCollect)
        internal val checkLike = findView<CheckedTextView>(R.id.checkLike)

        internal val imgSinglePicture = findView<ImageView>(R.id.imgSinglePicture)
        internal val tvPictureDescription = findView<TextView>(R.id.tvPictureDescription)
        internal val tvSeeMore = findView<TextView>(R.id.tvSeeMore)
    }
}
