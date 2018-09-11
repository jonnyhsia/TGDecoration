package com.tugou.decoration.page.home.muse

import com.tugou.decoration.model.home.entity.DecorTipModel
import com.tugou.decoration.model.muse.entity.AuthorModel
import com.tugou.decoration.model.muse.entity.MuseModel
import com.tugou.decoration.model.muse.entity.RecommendUserModel

interface ProgressTipsDelegate {

    fun tapTip(tip: DecorTipModel)
}

interface ContentDelegate {
    /**
     * 点击作者
     */
    fun tapAuthor(author: AuthorModel)

    /**
     * 点击关注
     */
    fun tapFollow(author: AuthorModel)
}

interface ArticleDelegate : ContentDelegate {

    /**
     * 点击内容详情
     */
    fun tapArticle(article: MuseModel.ArticleModel)

    /**
     * 点击喜欢内容
     */
    fun tapLike(article: MuseModel.ArticleModel, toLike: Boolean)

    /**
     * 点击收藏内容
     */
    fun tapCollect(article: MuseModel.ArticleModel, toCollect: Boolean)
}

interface AlbumDelegate : ContentDelegate {

    /**
     * 点击内容详情
     */
    fun tapAlbum(album: MuseModel.AlbumModel)

    /**
     * 点击喜欢内容
     */
    fun tapLike(album: MuseModel.AlbumModel, toLike: Boolean)

    /**
     * 点击收藏内容
     */
    fun tapCollect(album: MuseModel.AlbumModel, toCollect: Boolean)
}

interface SinglePictureDelegate : ContentDelegate {
    /**
     * 点击内容详情
     */
    fun tapPicture(picture: MuseModel.SinglePictureModel)

    /**
     * 点击喜欢内容
     */
    fun tapLike(picture: MuseModel.SinglePictureModel, toLike: Boolean)

    /**
     * 点击收藏内容
     */
    fun tapCollect(picture: MuseModel.SinglePictureModel, toCollect: Boolean)
}

interface RecommendUserDelegate {
    /**
     * 点击用户
     */
    fun tapRecommendUser(user: RecommendUserModel)
}