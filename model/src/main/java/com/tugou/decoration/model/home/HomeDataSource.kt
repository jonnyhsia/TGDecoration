package com.tugou.decoration.model.home

import com.tugou.decoration.model.base.TGDataSource
import com.tugou.decoration.model.home.entity.DailyRecommendModel
import com.tugou.decoration.model.home.entity.HomeConfigModel
import com.tugou.decoration.model.home.entity.InboxOverviewModel
import com.tugou.decoration.model.home.entity.MineConfigModel
import io.reactivex.Observable
import io.reactivex.Single

interface HomeDataSource : TGDataSource {

    /**
     * 获取每日推荐
     */
    fun getDailyRecommend(): Observable<DailyRecommendModel>

    /**
     * 获取收件箱概览
     */
    fun getInboxOverview(): Single<InboxOverviewModel>

    /**
     * 获取灵感页首页配置
     */
    fun getMuseConfig(): Observable<HomeConfigModel>

    /**
     * 获取商城页首页配置
     */
    fun getMallConfig(): Observable<HomeConfigModel>

    /**
     * 获取个人中心配置
     */
    fun getMineConfig(): Observable<MineConfigModel>
}