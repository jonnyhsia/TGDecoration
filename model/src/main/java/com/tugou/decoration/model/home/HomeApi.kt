package com.tugou.decoration.model.home

import com.tugou.decoration.model.base.TGResponse
import com.tugou.decoration.model.home.entity.DailyRecommendJsonModel
import com.tugou.decoration.model.home.entity.EntryModel
import com.tugou.decoration.model.home.entity.HomeConfigModel
import com.tugou.decoration.model.muse.entity.MallRecommendJsonModel
import io.reactivex.Single
import retrofit2.http.GET

interface HomeApi {

    @GET("home/mine-entries")
    fun getMineEntries(): Single<TGResponse<List<EntryModel>>>

    @GET("home/muse")
    fun getMuseConfig(): Single<TGResponse<HomeConfigModel>>

    @GET("home/recommend")
    fun getDailyRecommend(): Single<TGResponse<DailyRecommendJsonModel>>

    @GET("home/mall")
    fun getMallRecommend(): Single<TGResponse<MallRecommendJsonModel>>
}