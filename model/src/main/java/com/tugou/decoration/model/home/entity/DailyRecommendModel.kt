package com.tugou.decoration.model.home.entity

import com.google.gson.JsonArray

internal const val TYPE_DISCOVER = 1
internal const val TYPE_MAGAZINES = 2
internal const val TYPE_WARES = 3
internal const val TYPE_CONTENT_HEADER = 4
internal const val TYPE_ARTICLE = 5
internal const val TYPE_ALBUM = 6

data class DailyRecommendJsonModel(
        val page: Int,
        val list: JsonArray
)

data class DailyRecommendModel(
        val page: Int,
        val list: List<RecommendItemModel>
)

sealed class RecommendItemModel(val type: Int) {

    data class DiscoverModel(
            val title: String,
            val banners: List<HomeBannerModel>,
            val entries: List<EntryModel>
    ) : RecommendItemModel(TYPE_DISCOVER)

    data class MagazineListModel(
            val title: String,
            val magazines: List<MagazineModel>
    ) : RecommendItemModel(TYPE_MAGAZINES)

    data class WareListModel(
            val title: String,
            val moreAppUrl: String,
            val wares: List<RecommendWareModel>
    ) : RecommendItemModel(TYPE_WARES)

    data class ContentHeaderModel(
            val title: String,
            val tags: List<TagModel>
    ) : RecommendItemModel(TYPE_CONTENT_HEADER)

    data class RecommendArticleModel(
            val id: Int,
            val title: String,
            val cover: String,
            val tags: List<String>
    ) : RecommendItemModel(TYPE_ARTICLE)

    data class RecommendAlbumModel(
            val id: Int,
            val cover: String
    ) : RecommendItemModel(TYPE_ALBUM)
}

data class MagazineModel(
        val id: Int,
        val cover: String
)

data class RecommendWareModel(
        val id: Int,
        val image: String,
        val name: String,
        val recommendReason: String,
        val price: String,
        val label: String?
)

data class TagModel(
        val image: String,
        val appUrl: String,
        val text: String
)