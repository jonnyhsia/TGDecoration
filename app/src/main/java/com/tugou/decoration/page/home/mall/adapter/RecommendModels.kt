package com.tugou.decoration.page.home.mall.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tugou.decoration.R
import com.tugou.decoration.base.SimpleViewHolder
import com.tugou.decoration.ext.displayText
import com.tugou.decoration.ext.findView
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.muse.entity.MallRecommendItem
import com.tugou.decoration.model.muse.entity.MallRecommendItem.RecommendWareHeaderModel
import com.tugou.decoration.model.muse.entity.MallRecommendItem.RecommendWareModel
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

/**
 * 推荐商品标题
 */
class RecommendHeaderViewBinder : TGItemViewBinder<RecommendWareHeaderModel, SimpleViewHolder>() {

    override val itemViewRes = R.layout.item_recommend_ware_header

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): SimpleViewHolder {
        return SimpleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, item: RecommendWareHeaderModel) {
        (holder.itemView as TextView).text = item.title
    }
}

interface RecommendWareDelegate {
    /**
     * 点击推荐的商品
     */
    fun tapRecommendWare(ware: RecommendWareModel)
}

/**
 * 推荐的商品
 */
class RecommendWareViewBinder(
        delegate: RecommendWareDelegate
) : TGItemViewBinder<RecommendWareModel, RecommendWareViewBinder.ViewHolder>(), RecommendWareDelegate by delegate {

    override val itemViewRes = R.layout.item_recommend_ware

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: RecommendWareModel) {
        with(holder) {
            itemView.setOnClickListener { tapRecommendWare(item) }
            imgWare.load(item.image) { commonOption() }
            tvWareTitle.text = item.name
            tvWarePrice.text = SpannableString("¥ ${item.price}").apply {
                setSpan(AbsoluteSizeSpan(11, true), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }
            tvWarePriceLabel.displayText = item.priceLabel
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgWare = findView<ImageView>(R.id.imgWare)
        val tvWareTitle = findView<TextView>(R.id.tvWareTitle)
        val tvWarePrice = findView<TextView>(R.id.tvWarePrice)
        val tvWarePriceLabel = findView<TextView>(R.id.tvWarePriceLabel)

        init {
            findView<TextView>(R.id.tvWareDescription).visibility = View.GONE
        }
    }
}


interface RecommendSubjectDelegate {
    fun tapWareSubject(subject: MallRecommendItem.WareSubjectModel)
}

/**
 * 推荐的商品合辑
 */
class RecommendSubjectViewBinder(
        delegate: RecommendSubjectDelegate
) : TGItemViewBinder<MallRecommendItem.WareSubjectModel, SimpleViewHolder>(), RecommendSubjectDelegate by delegate {

    override val itemViewRes = R.layout.item_recommend_subject

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): SimpleViewHolder {
        return SimpleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, item: MallRecommendItem.WareSubjectModel) {
        (holder.itemView as ImageView).apply {
            load(item.image) { simpleOption() }
            setOnClickListener { tapWareSubject(item) }
        }
    }
}