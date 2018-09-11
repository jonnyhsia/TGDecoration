package com.tugou.decoration.page.home.mall.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.tugou.decoration.model.muse.entity.MallRecommendItem.ArticleCollectionModel
import com.tugou.decoration.model.muse.entity.MallRecommendItem.WareCollectionModel
import com.tugou.decoration.widget.foundation.TGTextView
import com.tugou.decoration.widget.recyclerview.BounceRecyclerView
import com.tugou.decoration.widget.recyclerview.SpacingItemDecoration
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

class CollectionViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvCollectionTitle = findView<TGTextView>(R.id.tvCollectionTitle)
    val recycleCollection = findView<BounceRecyclerView>(R.id.recycleCollection).apply {
        asHorizontalList()
        addItemDecoration(SpacingItemDecoration(0, 0, context.dp2px(10f).toInt(), 0).apply {
            edgeSpacingFactory = 0f
        })
        GravitySnapHelper(Gravity.START).attachToRecyclerView(this)
    }
}

class WareCollectionViewBinder(
        delegate: WareCollectionDelegate
) : TGItemViewBinder<WareCollectionModel, CollectionViewHolder>(), WareCollectionDelegate by delegate {

    override val itemViewRes = R.layout.item_ware_collection

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): CollectionViewHolder {
        return CollectionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, item: WareCollectionModel) {
        with(holder) {
            tvCollectionTitle.text = item.title
            recycleCollection.adapter = SimpleWareAdapter(item.wares).apply {
                setOnItemClickListener { pos ->
                    tapWareInCollection(item.wares[pos])
                }
            }
        }
    }
}

interface WareCollectionDelegate {
    fun tapWareInCollection(ware: WareCollectionModel.WareItemModel)
}

class ArticleCollectionViewBinder(
        delegate: ArticleCollectionDelegate
) : TGItemViewBinder<ArticleCollectionModel, CollectionViewHolder>(), ArticleCollectionDelegate by delegate {

    override val itemViewRes = R.layout.item_ware_collection

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): CollectionViewHolder {
        return CollectionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, item: ArticleCollectionModel) {
        with(holder) {
            tvCollectionTitle.text = item.title
            recycleCollection.adapter = SimpleArticleAdapter(item.articles).apply {
                setOnItemClickListener { pos ->
                    tapArticle(item.articles[pos])
                }
            }
        }
    }
}

class SimpleArticleAdapter(
        articles: List<ArticleCollectionModel.ArticleItemModel>
) : SimpleAdapter<ArticleCollectionModel.ArticleItemModel>(R.layout.item_simple_article, articles) {

    override fun onViewHolderCreated(holder: SimpleViewHolder) {
        super.onViewHolderCreated(holder)
        holder.itemView.apply {
            clipToCardOutline(4)
        }
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, item: ArticleCollectionModel.ArticleItemModel) {
        with(holder) {
            findView<ImageView>(R.id.imgCover).load(item.cover) { simpleOption() }
            findView<TextView>(R.id.tvArticleTitle).text = item.title
        }
    }
}

interface ArticleCollectionDelegate {
    /**
     * 点击文章详情
     */
    fun tapArticle(article: ArticleCollectionModel.ArticleItemModel)
}

class SimpleWareAdapter(
        wares: List<WareCollectionModel.WareItemModel>
) : SimpleAdapter<WareCollectionModel.WareItemModel>(R.layout.item_simple_ware, wares) {

    override fun onBindViewHolder(holder: SimpleViewHolder, item: WareCollectionModel.WareItemModel) {
        with(holder) {
            findView<ImageView>(R.id.imgWare).load(item.image) { commonOption() }
            findView<TextView>(R.id.tvWareTitle).text = "${item.name} | ${item.description}"
        }
    }
}