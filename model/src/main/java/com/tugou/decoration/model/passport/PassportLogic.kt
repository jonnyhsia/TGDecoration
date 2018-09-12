package com.tugou.decoration.model.passport

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.tugou.decoration.model.base.TGRepository
import com.tugou.decoration.model.passport.entity.UserModel

internal object PassportLogic : TGRepository(), PassportDataSource {
    private val liveUserModel = MutableLiveData<UserModel>()

    override fun preload() {
    }

    override fun getLoginUser() = TGRepository.getLoginUser()

    override fun isUserLogin(): Boolean {
        return false
    }

    override fun getPreference(): SharedPreferences {
        return contextRef.get()?.getSharedPreferences("passport", Context.MODE_PRIVATE)
                ?: throw RuntimeException("Application context is null.")
    }
}