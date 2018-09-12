package com.tugou.decoration.model

import android.content.Context
import com.tugou.decoration.model.base.TGRepository
import com.tugou.decoration.model.home.HomeDataSource
import com.tugou.decoration.model.home.HomeRepository
import com.tugou.decoration.model.mall.MallDataSource
import com.tugou.decoration.model.mall.MallRepository
import com.tugou.decoration.model.muse.MuseDataSource
import com.tugou.decoration.model.muse.MuseRepository
import com.tugou.decoration.model.passport.PassportDataSource
import com.tugou.decoration.model.passport.PassportLogic

object Repository {

    val passportDataSource: PassportDataSource
        get() = PassportLogic

    val homeDataSource: HomeDataSource
        get() = HomeRepository

    val museDataSource: MuseDataSource
        get() = MuseRepository

    val mallDataSource: MallDataSource
        get() = MallRepository

    fun initialize(context: Context) {
        TGRepository.initialize(context)
        passportDataSource.preload()
    }

}