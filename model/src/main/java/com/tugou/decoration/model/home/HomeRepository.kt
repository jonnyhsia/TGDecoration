package com.tugou.decoration.model.home

import com.tugou.decoration.model.base.*
import com.tugou.decoration.model.home.entity.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal object HomeRepository : TGRepository(), HomeDataSource {

    private val homeApi = retrofit.create<HomeApi>()

    override fun preload() {
    }

    override fun getDailyRecommend(): Observable<DailyRecommendModel> {
        return homeApi.getDailyRecommend()
                .subscribeOn(Schedulers.io())
                .handleResponse()
                .map { jsonModel ->
                    val gson = typeSafeGson
                    val itemList = ArrayList<RecommendItemModel>()
                    for (element in jsonModel.list) {
                        val obj = element.asJsonObject
                        val itemModel = when (obj["type"].asInt) {
                            TYPE_DISCOVER -> gson.fromJson<RecommendItemModel.DiscoverModel>(obj)
                            TYPE_MAGAZINES -> gson.fromJson<RecommendItemModel.MagazineListModel>(obj)
                            TYPE_WARES -> gson.fromJson<RecommendItemModel.WareListModel>(obj)
                            TYPE_CONTENT_HEADER -> gson.fromJson<RecommendItemModel.ContentHeaderModel>(obj)
                            TYPE_ARTICLE -> gson.fromJson<RecommendItemModel.RecommendArticleModel>(obj)
                            TYPE_ALBUM -> gson.fromJson<RecommendItemModel.RecommendAlbumModel>(obj)
                            else -> null
                        } ?: continue

                        itemList.add(itemModel)
                    }
                    DailyRecommendModel(jsonModel.page, itemList)
                }
                .relateToLocal(HomeLocalRepository.getLocalDailyRecommend())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMuseConfig(): Observable<HomeConfigModel> {
        return homeApi.getMuseConfig()
                .scheduler()
                .handleResponse()
                .relateToLocal(HomeLocalRepository.getLocalMuseConfig())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMallConfig(): Observable<HomeConfigModel> {
        return homeApi.getMallConfig()
                .subscribeOn(Schedulers.io())
                .handleResponse()
                .relateToLocal(HomeLocalRepository.getLocalMallConfig())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMineConfig(): Observable<MineConfigModel> {
        return homeApi.getMineConfig()
                .subscribeOn(Schedulers.io())
                .handleResponse()
                .relateToLocal(HomeLocalRepository.getLocalMineEntries())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getInboxOverview(): Single<InboxOverviewModel> {
        TODO("not implemented")
    }
}