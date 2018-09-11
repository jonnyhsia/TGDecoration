package com.tugou.decoration.model.home

import com.tugou.decoration.model.base.TGDataSource
import com.tugou.decoration.model.home.entity.*
import com.tugou.decoration.model.muse.entity.MallRecommendModel
import io.reactivex.Flowable
import io.reactivex.Single

interface HomeDataSource : TGDataSource {

    fun getExploreData(): Single<Any>

    fun getDailyRecommend(): Single<DailyRecommendModel>

    fun checkInboxUnreadCount(): Single<Int>

    fun getInboxOverview(): Single<InboxOverviewModel>

    fun getMineEntries(): Flowable<List<EntryModel>>

    fun getRecentActivities(): Single<List<RecentActivityModel>>

    fun getMuseConfig(): Flowable<HomeConfigModel>

    fun getMallConfig(): Flowable<HomeConfigModel>

    fun getMallRecommend(): Flowable<MallRecommendModel>
}