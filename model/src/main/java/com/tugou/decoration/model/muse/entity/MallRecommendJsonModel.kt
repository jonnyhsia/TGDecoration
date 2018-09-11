package com.tugou.decoration.model.muse.entity

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.tugou.decoration.model.base.fromJson


internal const val TYPE_WARE_COLLECTION_WARE = 0
internal const val TYPE_WARE_COLLECTION_ARTICLE = 1
internal const val TYPE_WARE_WARE_HEADER = 2
internal const val TYPE_WARE_RECOMMEND_WARE = 3
internal const val TYPE_WARE_SUBJECT_WARE = 4

data class MallRecommendJsonModel(
        val page: Int,
        val list: JsonArray
) {
    fun toModel(): MallRecommendModel {
        val gson = Gson()
        val itemList = ArrayList<MallRecommendItem>(list.size())
        for (element in list) {
            val obj = element.asJsonObject
            val item: MallRecommendItem = when (obj["type"].asInt) {
                TYPE_WARE_COLLECTION_WARE -> gson.fromJson<MallRecommendItem.WareCollectionModel>(obj)
                TYPE_WARE_COLLECTION_ARTICLE -> gson.fromJson<MallRecommendItem.ArticleCollectionModel>(obj)
                TYPE_WARE_WARE_HEADER -> gson.fromJson<MallRecommendItem.RecommendWareHeaderModel>(obj)
                TYPE_WARE_RECOMMEND_WARE -> gson.fromJson<MallRecommendItem.RecommendWareModel>(obj)
                TYPE_WARE_SUBJECT_WARE -> gson.fromJson<MallRecommendItem.WareSubjectModel>(obj)
                else -> null
            } ?: continue
            itemList.add(item)
        }
        return MallRecommendModel(page, itemList)
    }
}

data class MallRecommendModel(
        val page: Int,
        val list: List<MallRecommendItem>
)

sealed class MallRecommendItem(
        val type: Int
) {
    data class WareCollectionModel(
            val title: String,
            val wares: List<WareItemModel>
    ) : MallRecommendItem(TYPE_WARE_COLLECTION_WARE) {

        data class WareItemModel(
                val name: String,
                val description: String,
                val image: String,
                val destination: String
        )
    }

    data class ArticleCollectionModel(
            val title: String,
            val articles: List<ArticleItemModel>
    ) : MallRecommendItem(TYPE_WARE_COLLECTION_ARTICLE) {

        data class ArticleItemModel(
                val cover: String,
                val title: String,
                val destination: String
        )
    }

    data class RecommendWareHeaderModel(
            val title: String
    ) : MallRecommendItem(TYPE_WARE_WARE_HEADER)

    data class RecommendWareModel(
            val name: String,
            val image: String,
            val destination: String,
            val price: String,
            val priceLabel: String?
    ) : MallRecommendItem(TYPE_WARE_RECOMMEND_WARE)

    data class WareSubjectModel(
            val image: String,
            val destination: String
    ) : MallRecommendItem(TYPE_WARE_SUBJECT_WARE)
}