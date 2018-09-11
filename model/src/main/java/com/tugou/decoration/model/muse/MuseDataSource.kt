package com.tugou.decoration.model.muse

import com.tugou.decoration.model.base.TGDataSource
import com.tugou.decoration.model.muse.entity.MuseTimelineModel
import io.reactivex.Single

interface MuseDataSource : TGDataSource {

    fun getMuseTimeline(page: Int): Single<MuseTimelineModel>
}