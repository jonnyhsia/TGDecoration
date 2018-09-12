package com.tugou.decoration.model.mall

import com.tugou.decoration.model.base.TGResponse
import com.tugou.decoration.model.muse.entity.MallRecommendJsonModel
import io.reactivex.Single
import retrofit2.http.GET

interface MallApi {

    /**
     * 商城推荐流接口
     */
    @GET("mall/recommend")
    fun getMallRecommend(): Single<TGResponse<MallRecommendJsonModel>>

}