package com.tugou.decoration.page.home.recommend.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.decoration.R
import com.tugou.decoration.base.SimpleAdapter
import com.tugou.decoration.base.SimpleViewHolder
import com.tugou.decoration.ext.asGridList
import com.tugou.decoration.ext.displayText
import com.tugou.decoration.ext.findView
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.home.entity.RecommendItemModel
import com.tugou.decoration.model.home.entity.RecommendWareModel
import com.tugou.decoration.widget.recyclerview.SpacingItemDecoration
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

interface WareListDelegate {
    /**
     * 点击查看更多商品
     */
    fun tapMoreWare(destination: String)

    /**
     * 点击查看商品
     */
    fun tapWare(ware: RecommendWareModel)
}

class WareListViewBinder(
        delegate: WareListDelegate
) : TGItemViewBinder<RecommendItemModel.WareListModel, WareListViewBinder.ViewHolder>(), WareListDelegate by delegate {

    override val itemViewRes = R.layout.item_recommend_ware_list

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: RecommendItemModel.WareListModel) {
        with(holder) {
            tvWaresTitle.text = item.title
            tvMoreWare.setOnClickListener {
                tapMoreWare(item.moreAppUrl)
            }
            recycleRecommendWare.adapter = RecommendWareAdapter(item.wares)
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWaresTitle = findView<TextView>(R.id.tvWaresTitle)
        val tvMoreWare = findView<TextView>(R.id.tvMoreWare)
        val recycleRecommendWare = findView<RecyclerView>(R.id.recycleRecommendWare).apply {
            asGridList(2)
            val horizontalSpacing = context.dp2px(6f).toInt()
            addItemDecoration(SpacingItemDecoration(horizontalSpacing, 0).apply {
                edgeSpacingFactory = 0f
            })
        }
    }

    inner class RecommendWareAdapter(wares: List<RecommendWareModel>) : SimpleAdapter<RecommendWareModel>(R.layout.item_recommend_ware, wares) {

        override fun onBindViewHolder(holder: SimpleViewHolder, item: RecommendWareModel) {
            with(holder) {
                findView<ImageView>(R.id.imgWare).load(item.image) { commonOption() }
                findView<TextView>(R.id.tvWareTitle).text = item.name
                findView<TextView>(R.id.tvWareDescription).text = item.recommendReason
                findView<TextView>(R.id.tvWarePrice).text = SpannableString("¥ ${item.price}").apply {
                    setSpan(AbsoluteSizeSpan(11, true), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                }
                findView<TextView>(R.id.tvWarePriceLabel).displayText = item.label
                itemView.setOnClickListener {
                    tapWare(item)
                }
            }
        }
    }
}