package com.tugou.decoration.page.home.recommend

import androidx.lifecycle.MutableLiveData
import com.tugou.core.TGViewModel
import com.tugou.core.addTo
import com.tugou.decoration.model.Repository
import com.tugou.decoration.model.home.entity.*
import com.tugou.decoration.page.home.recommend.adapter.*

class RecommendViewModel : TGViewModel(), DiscoverDelegate, MagazinesDelegate, WareListDelegate, HeaderDelegate, RecommendArticleDelegate, RecommendAlbumDelegate {

    /** Home 数据源 */
    private val homeDataSource = Repository.homeDataSource

    /** Explore 页的数据 */
    internal val exploreData = MutableLiveData<Any>()

    internal val dailyRecommend = MutableLiveData<DailyRecommendModel>()

    /** 获取 Explore 页数据 */
    fun fetchExploreData() {
        homeDataSource.getDailyRecommend()
                .subscribe({
                    dailyRecommend.postValue(it)
                }, {
                    toast(it.message)
                })
                .addTo(disposables)
//        response.postValue(RequestState.StateFetching)
//        homeDataSource.getExploreData()
//                .subscribe({
//                    response.postValue(RequestState.StateSuccess)
//                    exploreData.postValue(it)
//                }, {
//                    response.postValue(RequestState.StateFailed(it.message.toString()))
//                })
//                .addTo(disposables)
    }

    override fun tapBanner(banner: HomeBannerModel) {
        toast("Banner")
    }

    override fun tapEntry(entry: EntryModel) {
        toast("入口")
    }

    override fun tapMagazine(magazineModel: MagazineModel) {
        toast("杂志")
    }

    override fun tapMoreWare(destination: String) {
        toast("更多")
    }

    override fun tapRecommendTag(tag: TagModel) {
        toast("推荐标签")
    }

    override fun tapArticle(article: RecommendItemModel.RecommendArticleModel) {
        toast("文章")
    }

    override fun tapWare(ware: RecommendWareModel) {
        toast("商品")
    }

    override fun tapAlbum(album: RecommendItemModel.RecommendAlbumModel) {
        toast("图辑")
    }
}