package com.tugou.decoration.model.muse

import com.tugou.decoration.model.base.TGResponse
import com.tugou.decoration.model.muse.entity.MuseTimelineJsonModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MuseApi {

    @GET("/muse/timeline")
    fun getMuseTimeline(@Query("page") page: Int): Single<TGResponse<MuseTimelineJsonModel>>

}