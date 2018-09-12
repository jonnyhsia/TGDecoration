package com.tugou.decoration.model.mall

import com.tugou.decoration.model.muse.entity.MallRecommendModel
import io.reactivex.Observable

interface MallDataSource {

    /**
     * 获取商城推荐
     */
    fun getMallRecommend(): Observable<MallRecommendModel>
}