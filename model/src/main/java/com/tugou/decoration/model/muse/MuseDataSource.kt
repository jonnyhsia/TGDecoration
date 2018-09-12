package com.tugou.decoration.model.muse

import com.tugou.decoration.model.base.TGDataSource
import com.tugou.decoration.model.muse.entity.MuseTimelineModel
import io.reactivex.Observable

interface MuseDataSource : TGDataSource {

    /**
     * 获取灵感时间线
     */
    fun getMuseTimeline(page: Int): Observable<MuseTimelineModel>
}