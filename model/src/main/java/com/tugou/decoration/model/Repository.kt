package com.tugou.decoration.model

import android.content.Context
import com.tugou.decoration.model.base.TGLogic
import com.tugou.decoration.model.home.HomeDataSource
import com.tugou.decoration.model.home.HomeLogic
import com.tugou.decoration.model.muse.MuseDataSource
import com.tugou.decoration.model.muse.MuseLogic
import com.tugou.decoration.model.passport.PassportDataSource
import com.tugou.decoration.model.passport.PassportLogic

object Repository {

    val passportDataSource: PassportDataSource
        get() = PassportLogic

    val homeDataSource: HomeDataSource
        get() = HomeLogic

    val museDataSource: MuseDataSource
        get() = MuseLogic

    fun initialize(context: Context) {
        TGLogic.initialize(context)
        passportDataSource.preload()
    }

}