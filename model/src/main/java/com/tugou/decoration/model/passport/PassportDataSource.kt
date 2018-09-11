package com.tugou.decoration.model.passport

import android.content.SharedPreferences
import com.tugou.decoration.model.base.TGDataSource
import com.tugou.decoration.model.passport.entity.UserModel

interface PassportDataSource : TGDataSource {

    fun isUserLogin(): Boolean

    fun getLoginUser(): UserModel?

    fun getPreference(): SharedPreferences

}