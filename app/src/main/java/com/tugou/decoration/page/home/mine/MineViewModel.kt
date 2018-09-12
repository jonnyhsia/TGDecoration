package com.tugou.decoration.page.home.mine

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.tugou.core.HubMessage
import com.tugou.core.TGViewModel
import com.tugou.core.addTo
import com.tugou.decoration.model.Repository
import com.tugou.decoration.model.home.entity.EntryModel
import com.tugou.decoration.model.home.entity.RecentActivityModel
import com.tugou.decoration.model.passport.entity.UserModel

class MineViewModel : TGViewModel(), SharedPreferences.OnSharedPreferenceChangeListener, MineEntryDelegate, RecentActivityDelegate {

    private val homeDataSource = Repository.homeDataSource
    private val passportDataSource = Repository.passportDataSource

    internal val entries = MutableLiveData<List<EntryModel>>()

    internal val recentActivities = MutableLiveData<List<RecentActivityModel>>()
    internal val loginUser = MutableLiveData<UserModel?>()

    init {
        passportDataSource.getPreference()
                .registerOnSharedPreferenceChangeListener(this)
    }

    fun fetchUserInfo() {
        loginUser.postValue(passportDataSource.getLoginUser())
    }

    /**
     * 获取入口与最新的活动列表
     */
    fun fetchMineConfig() {
        homeDataSource.getMineConfig()
                .subscribe({
                    entries.postValue(it.entries)
                    recentActivities.postValue(it.recentActivities)
                }, { error ->
                    error.message?.let { messageHub.postValue(HubMessage.DisplayMsg(it)) }
                })
                .addTo(disposables)
    }

    fun tapEditProfile() {
    }

    fun tapRelationship() {
    }

    fun tapSettings() {
    }

    fun tapProfile() {
    }

    fun tapTugouVip() {
    }

    /**
     * 点击个人中心入口
     */
    override fun tapEntry(entry: EntryModel) {
        navigate(entry.destination)
    }

    /**
     * 点击最近活动
     */
    override fun tapRecentActivity(activity: RecentActivityModel) {
        navigate(activity.destination)
    }

    fun tapFeedback() {
    }

    fun tapInviteFriend() {
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "user_id") {
            loginUser.postValue(passportDataSource.getLoginUser())
        }
    }

    override fun onCleared() {
        passportDataSource.getPreference()
                .unregisterOnSharedPreferenceChangeListener(this)
        super.onCleared()
    }
}