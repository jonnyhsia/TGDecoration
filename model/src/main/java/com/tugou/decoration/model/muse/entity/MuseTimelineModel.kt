package com.tugou.decoration.model.muse.entity

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import com.tugou.decoration.model.home.entity.DecorProgressTipsModel


internal const val TYPE_ARTICLE = 0
internal const val TYPE_ALBUM = 10
internal const val TYPE_SINGLE_PICTURE = 11
internal const val TYPE_RECOMMEND_USER = 20
internal const val TYPE_DECOR_TIPS = 30

data class MuseTimelineJsonModel(
        val page: Int,
        val total: Int,
        @SerializedName("daily_list") val museList: JsonArray
)

data class MuseTimelineModel(
        val page: Int,
        val total: Int,
        val museList: List<MuseModel>
)

sealed class MuseModel(val type: Int) {

    data class TipModel(
            val title: String,
            val list: List<DecorProgressTipsModel>
    ) : MuseModel(TYPE_DECOR_TIPS)

    /**
     * 灵感文章
     */
    data class ArticleModel(
            val id: Int,
            val title: String,
            val cover: String,
            val author: AuthorModel,
            val createTime: String,
            val recommendReason: String,
            val behavior: MuseBehavior
    ) : MuseModel(TYPE_ARTICLE)

    /**
     * 灵感图辑
     */
    class AlbumModel(
            val id: Int,
            val cover: String,
            val author: AuthorModel,
            val createTime: String,
            val recommendReason: String,
            val behavior: MuseBehavior
    ) : MuseModel(TYPE_ALBUM)

    /**
     * 灵感单图
     */
    class SinglePictureModel(
            val id: Int,
            val description: String,
            val image: String,
            val aspectRatio: Float,
            val author: AuthorModel,
            val createTime: String,
            val recommendReason: String,
            val behavior: MuseBehavior
    ) : MuseModel(TYPE_SINGLE_PICTURE)

    /**
     * 推荐用户
     */
    class RecommendUserListModel(
            val title: String,
            val userList: List<RecommendUserModel>
    ) : MuseModel(TYPE_RECOMMEND_USER)
}

data class MuseBehavior(
        val likeAmount: Int,
        val collectAmount: Int,
        val isLike: Boolean,
        val isCollect: Boolean
)

data class AuthorModel(
        val id: Int,
        val name: String,
        val avatar: String,
        val description: String,
        val isFollowing: Boolean
)

data class RecommendUserModel(
        val id: Int,
        val name: String,
        val avatar: String,
        val rank: String,
        val recommendReason: String?
)