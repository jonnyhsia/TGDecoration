package com.tugou.decoration.model.mall

import com.tugou.decoration.model.base.TGRepository
import com.tugou.decoration.model.base.create
import com.tugou.decoration.model.base.handleResponse
import com.tugou.decoration.model.base.relateToLocal
import com.tugou.decoration.model.muse.entity.MallRecommendModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal object MallRepository : TGRepository(), MallDataSource {

    private val mallApi = retrofit.create<MallApi>()

    override fun getMallRecommend(): Observable<MallRecommendModel> {
        return mallApi.getMallRecommend()
                .subscribeOn(Schedulers.io())
                .handleResponse()
                .map { it.toModel() }
                .relateToLocal(MallLocalRepository.getLocalMallRecommend())
                .observeOn(AndroidSchedulers.mainThread())
    }
}