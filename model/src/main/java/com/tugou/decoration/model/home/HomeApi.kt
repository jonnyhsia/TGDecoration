package com.tugou.decoration.model.home

import com.tugou.decoration.model.base.TGResponse
import com.tugou.decoration.model.home.entity.DailyRecommendJsonModel
import com.tugou.decoration.model.home.entity.HomeConfigModel
import com.tugou.decoration.model.home.entity.MineConfigModel
import io.reactivex.Single
import retrofit2.http.GET

interface HomeApi {

    /**
     * 每日推荐接口
     */
    @GET("home/recommend")
    fun getDailyRecommend(): Single<TGResponse<DailyRecommendJsonModel>>

    /**
     * 灵感首页配置接口
     */
    @GET("home/muse-config")
    fun getMuseConfig(): Single<TGResponse<HomeConfigModel>>

    /**
     * 商城首页配置接口
     */
    @GET("home/mall-config")
    fun getMallConfig(): Single<TGResponse<HomeConfigModel>>

    /**
     * 个人中心首页配置接口
     */
    @GET("home/mine-config")
    fun getMineConfig(): Single<TGResponse<MineConfigModel>>
}