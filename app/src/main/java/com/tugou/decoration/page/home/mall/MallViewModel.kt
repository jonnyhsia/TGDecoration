package com.tugou.decoration.page.home.mall

import androidx.lifecycle.MutableLiveData
import com.tugou.core.TGViewModel
import com.tugou.core.addTo
import com.tugou.decoration.model.Repository
import com.tugou.decoration.model.home.entity.EntryModel
import com.tugou.decoration.model.home.entity.HomeBannerModel
import com.tugou.decoration.model.home.entity.HomeConfigModel
import com.tugou.decoration.model.muse.entity.MallRecommendItem
import com.tugou.decoration.model.muse.entity.MallRecommendModel
import com.tugou.decoration.page.home.BannerDelegate
import com.tugou.decoration.page.home.HomeEntryDelegate
import com.tugou.decoration.page.home.mall.adapter.ArticleCollectionDelegate
import com.tugou.decoration.page.home.mall.adapter.RecommendSubjectDelegate
import com.tugou.decoration.page.home.mall.adapter.RecommendWareDelegate
import com.tugou.decoration.page.home.mall.adapter.WareCollectionDelegate

class MallViewModel : TGViewModel(), BannerDelegate, HomeEntryDelegate, WareCollectionDelegate, ArticleCollectionDelegate, RecommendWareDelegate, RecommendSubjectDelegate {

    private val homeDataSource = Repository.homeDataSource
    private val mallDataSource = Repository.mallDataSource

    internal val mallConfig = MutableLiveData<HomeConfigModel>()

    internal val mallRecommend = MutableLiveData<MallRecommendModel>()

    fun fetchMallConfig() {
        homeDataSource.getMallConfig()
                .subscribe({
                    mallConfig.postValue(it)
                }, {
                    toast(it.message)
                })
                .addTo(disposables)
    }

    fun fetchRecommendList() {
        mallDataSource.getMallRecommend()
                .subscribe({
                    mallRecommend.postValue(it)
                }, {

                })
                .addTo(disposables)
    }

    override fun tapBanner(banner: HomeBannerModel) {
    }

    override fun tapEntry(entry: EntryModel) {
    }

    override fun tapWareInCollection(ware: MallRecommendItem.WareCollectionModel.WareItemModel) {
    }

    override fun tapArticle(article: MallRecommendItem.ArticleCollectionModel.ArticleItemModel) {
    }

    override fun tapRecommendWare(ware: MallRecommendItem.RecommendWareModel) {
    }

    override fun tapWareSubject(subject: MallRecommendItem.WareSubjectModel) {
    }
}