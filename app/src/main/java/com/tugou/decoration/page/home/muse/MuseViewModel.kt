package com.tugou.decoration.page.home.muse

import androidx.lifecycle.MutableLiveData
import com.tugou.core.TGViewModel
import com.tugou.core.addTo
import com.tugou.decoration.model.Repository
import com.tugou.decoration.model.home.entity.DecorTipModel
import com.tugou.decoration.model.home.entity.EntryModel
import com.tugou.decoration.model.home.entity.HomeBannerModel
import com.tugou.decoration.model.home.entity.HomeConfigModel
import com.tugou.decoration.model.muse.entity.AuthorModel
import com.tugou.decoration.model.muse.entity.MuseModel
import com.tugou.decoration.model.muse.entity.RecommendUserModel
import com.tugou.decoration.page.home.BannerDelegate
import com.tugou.decoration.page.home.HomeEntryDelegate

class MuseViewModel : TGViewModel(), BannerDelegate, HomeEntryDelegate, ProgressTipsDelegate, ArticleDelegate,
        AlbumDelegate, SinglePictureDelegate, RecommendUserDelegate {

    private val homeDataSource = Repository.homeDataSource
    private val museDataSource = Repository.museDataSource

    private val headerData = MutableLiveData<Any>()

    internal val museConfig = MutableLiveData<HomeConfigModel>()
    internal val museTimeline = MutableLiveData<List<MuseModel>>()

    private val listData = ArrayList<MuseModel>()

    private var currentPage = 0

    fun fetchConfig() {
        homeDataSource.getMuseConfig()
                .subscribe({
                    museConfig.postValue(it)
                }, {
                    toast(it.message)
                })
                .addTo(disposables)
    }

    fun fetchTimeline() {
        // if (currentPage == 0 && headerData.value == null) {
        // }
        museDataSource.getMuseTimeline(currentPage + 1)
                .subscribe({
                    currentPage++
                    listData.addAll(it.museList)
                    museTimeline.postValue(listData)
                }, {
                })
                .addTo(disposables)
    }

    override fun tapBanner(banner: HomeBannerModel) {
    }

    override fun tapEntry(entry: EntryModel) {
    }

    override fun tapTip(tip: DecorTipModel) {

    }

    override fun tapAuthor(author: AuthorModel) {
        navigate("decor://Author?id=${author.id}")
    }

    override fun tapFollow(author: AuthorModel) {
    }

    override fun tapRecommendUser(user: RecommendUserModel) {

    }

    override fun tapArticle(article: MuseModel.ArticleModel) {
        navigate("decor://Article?id=${article.id}")
    }

    override fun tapAlbum(album: MuseModel.AlbumModel) {
        navigate("decor://Album?id=${album.id}")
    }

    override fun tapPicture(picture: MuseModel.SinglePictureModel) {
        navigate("decor://Picture?id=${picture.id}")
    }

    override fun tapLike(article: MuseModel.ArticleModel, toLike: Boolean) {
    }

    override fun tapLike(album: MuseModel.AlbumModel, toLike: Boolean) {
    }

    override fun tapLike(picture: MuseModel.SinglePictureModel, toLike: Boolean) {
    }

    override fun tapCollect(article: MuseModel.ArticleModel, toCollect: Boolean) {
    }

    override fun tapCollect(album: MuseModel.AlbumModel, toCollect: Boolean) {
    }

    override fun tapCollect(picture: MuseModel.SinglePictureModel, toCollect: Boolean) {
    }
}