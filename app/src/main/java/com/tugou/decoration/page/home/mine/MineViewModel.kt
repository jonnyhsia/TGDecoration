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

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "user_id") {
            loginUser.postValue(passportDataSource.getLoginUser())
        }
    }

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
     * 获取入口
     */
    fun fetchMineEntries() {
        homeDataSource.getMineEntries()
                .subscribe({
                    entries.postValue(it)
                }, { error ->
                    error.message?.let { messageHub.postValue(HubMessage.DisplayMsg(it)) }
                })
                .addTo(disposables)
    }

    /**
     * 获取最新的活动列表
     */
    fun fetchRecentActivities() {
        homeDataSource.getRecentActivities()
                .subscribe({
                    recentActivities.postValue(it)
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

    override fun tapEntry(entry: EntryModel) {
    }

    fun tapTugouVip() {

    }

    /**
     * 点击个人中心入口
     */
    fun tapEntry(entryPos: Int) {
        val entryModel = entries.value?.get(entryPos) ?: return
        messageHub.postValue(HubMessage.RouteMsg(entryModel.destination))
    }

    /**
     * 点击最近活动
     */
    override fun tapRecentActivity(activity: RecentActivityModel) {
        messageHub.postValue(HubMessage.RouteMsg(activity.destination))
    }

    fun tapFeedback() {
    }

    fun tapInviteFriend() {
    }


    override fun onCleared() {
        passportDataSource.getPreference()
                .unregisterOnSharedPreferenceChangeListener(this)
        super.onCleared()
    }
}