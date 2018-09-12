package com.tugou.decoration.model.muse

import com.tugou.decoration.model.base.*
import com.tugou.decoration.model.muse.entity.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal object MuseRepository : TGRepository(), MuseDataSource {

    private val museApi = retrofit.create<MuseApi>()

    override fun preload() {
    }

    override fun getMuseTimeline(page: Int): Observable<MuseTimelineModel> {
        return museApi.getMuseTimeline(page)
                .subscribeOn(Schedulers.io())
                .handleResponse()
                .map { jsonModel ->
                    val jsonArray = jsonModel.museList
                    val gson = typeSafeGson
                    val museList = ArrayList<MuseModel>()

                    for (element in jsonArray) {
                        val obj = element.asJsonObject
                        // 根据 obj 的 type 字段, 转换成对应类型的 Model
                        val muse: MuseModel? = when (obj["type"].asInt) {
                            TYPE_ARTICLE -> gson.fromJson<MuseModel.ArticleModel>(obj)
                            TYPE_ALBUM -> gson.fromJson<MuseModel.AlbumModel>(obj)
                            TYPE_SINGLE_PICTURE -> gson.fromJson<MuseModel.SinglePictureModel>(obj)
                            TYPE_RECOMMEND_USER -> gson.fromJson<MuseModel.RecommendUserListModel>(obj)
                            else -> null
                        }
                        muse?.let(museList::add)
                    }

                    MuseTimelineModel(jsonModel.page, jsonModel.total, museList)
                }
                .relateToLocal(MuseLocalRepository.getLocalMuseTimeline())
                .observeOn(AndroidSchedulers.mainThread())
    }
}