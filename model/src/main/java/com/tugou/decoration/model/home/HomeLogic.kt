package com.tugou.decoration.model.home

import com.google.gson.Gson
import com.tugou.decoration.model.R
import com.tugou.decoration.model.base.*
import com.tugou.decoration.model.home.entity.*
import com.tugou.decoration.model.muse.entity.MallRecommendItem
import com.tugou.decoration.model.muse.entity.MallRecommendItem.ArticleCollectionModel.ArticleItemModel
import com.tugou.decoration.model.muse.entity.MallRecommendItem.WareCollectionModel.WareItemModel
import com.tugou.decoration.model.muse.entity.MallRecommendModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

internal object HomeLogic : TGLogic(), HomeDataSource {
    private val homeApi = sRetrofit.create<HomeApi>()

    override fun getMallConfig(): Flowable<HomeConfigModel> {
        return Flowable.just(HomeConfigModel(
                banners = listOf(
                        HomeBannerModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/53522711.jpg", "decor://WareSubject?id=2"),
                        HomeBannerModel("http://ou4f31a1x.bkt.clouddn.com/18-9-8/38706980.jpg", "decor://WareSubject?id=1"),
                        HomeBannerModel("", "")
                ),
                entries = listOf(
                        EntryModel(R.drawable.ic_mall_design, "到家设计", "decor://HomeDesign"),
                        EntryModel(R.drawable.ic_mall_category, "臻品分类", "decor://WareCategories"),
                        EntryModel(R.drawable.ic_mall_limit, "限时抢购", "https://tugou.com/ware-time-limited"),
                        EntryModel(R.drawable.ic_mall_lab, "测评实验室", "decor://DecorReview")
                )
        ))
    }

    override fun getDailyRecommend(): Single<DailyRecommendModel> {
        return homeApi.getDailyRecommend()
                .subscribeOn(Schedulers.io())
                .handleResponse()
                .map { jsonModel ->
                    val itemList = ArrayList<RecommendItemModel>()
                    val gson = Gson()
                    for (element in jsonModel.list) {
                        val obj = element.asJsonObject
                        val itemModel = when (obj["type"].asInt) {
                            TYPE_DISCOVER -> gson.fromJson<RecommendItemModel.DiscoverModel>(obj)
                            TYPE_MAGAZINES -> gson.fromJson<RecommendItemModel.MagazineListModel>(obj)
                            TYPE_WARES -> gson.fromJson<RecommendItemModel.WareListModel>(obj)
                            TYPE_CONTENT_HEADER -> gson.fromJson<RecommendItemModel.ContentHeaderModel>(obj)
                            TYPE_ARTICLE -> gson.fromJson<RecommendItemModel.RecommendArticleModel>(obj)
                            TYPE_ALBUM -> gson.fromJson<RecommendItemModel.RecommendAlbumModel>(obj)
                            else -> null
                        } ?: continue

                        itemList.add(itemModel)
                    }
                    DailyRecommendModel(jsonModel.page, itemList)
                }
                .onErrorResumeNext { getLocalDailyRecommend() }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMallRecommend(): Flowable<MallRecommendModel> {
        return homeApi.getMallRecommend()
                .subscribeOn(Schedulers.io())
                .handleResponse()
                .map { it.toModel() }
                .onErrorResumeNext { getLocalMallRecommend() }
                .toFlowable()
                // .mergeWith(getLocalMallRecommend())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getLocalMallRecommend(): Single<MallRecommendModel> {
        return Single.just(MallRecommendModel(1, listOf(
                MallRecommendItem.WareCollectionModel("新上架臻品", listOf(
                        WareItemModel("VARIERA 盒子", "节省翻找时间，更多烹饪乐趣", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/58848006.jpg", ""),
                        WareItemModel("FABRIKÖR 门柜 ", "陈列和保护您的玻璃杯和心爱的收藏品。", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/25634404.jpg", ""),
                        WareItemModel("LÖVBACKEN 边桌", "经久耐用，不易弄脏，清洁方便。", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/90427417.jpg", ""),
                        WareItemModel("VARIERA 盒子", "节省翻找时间，更多烹饪乐趣", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/58848006.jpg", ""),
                        WareItemModel("FABRIKÖR 门柜 ", "陈列和保护您的玻璃杯和心爱的收藏品。", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/25634404.jpg", ""),
                        WareItemModel("", "", "", "decor://WareList")
                )),
                MallRecommendItem.ArticleCollectionModel("臻品测评", listOf(
                        ArticleItemModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/64410752.jpg", "宜家沙发测评，经典款 VS 夏季新款,哪个更胜一筹?", ""),
                        ArticleItemModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/15478349.jpg", "好评一篇的米家扫地机器人，真的有这么好用吗？", ""),
                        ArticleItemModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/64410752.jpg", "宜家沙发测评，经典款 VS 夏季新款,哪个更胜一筹?", ""),
                        ArticleItemModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/15478349.jpg", "好评一篇的米家扫地机器人，真的有这么好用吗？", ""),
                        ArticleItemModel("", "", "decor://ArticleList?tag=10")
                )),
                MallRecommendItem.RecommendWareHeaderModel("臻选推荐"),
                MallRecommendItem.RecommendWareModel("GUALÖV 格拉瓦 - 储物桌 黑色", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/48955200.jpg", "decor://", "2,499", "特惠抢购"),
                MallRecommendItem.RecommendWareModel("SÖDERHAMN 索德汉 - 三人沙发, 芬斯塔 天蓝色", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/77553511.jpg", "decor://", "4,499", "全网最低"),
                MallRecommendItem.WareSubjectModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/24891682.jpg", "decor://"),
                MallRecommendItem.RecommendWareModel("KALANCHOE - 长寿花 盆栽植物", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/95633461.jpg", "decor://", "19.90", null),
                MallRecommendItem.RecommendWareModel("SMAKMÄSSIG 斯玛麦希 - 玻璃水杯 6件", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/19207029.jpg", "decor://", "59.90", null),
                MallRecommendItem.WareSubjectModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/27650299.jpg", "decor://"),
                MallRecommendItem.RecommendWareModel("ChairUnknown 忘记是什么椅子了", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/52477206.jpg", "decor://", "149.00", null),
                MallRecommendItem.RecommendWareModel("Potted 绿油油 - 盆栽植物", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/90543723.jpg", "decor://", "79.90", null),
                MallRecommendItem.RecommendWareModel("GUALÖV 格拉瓦 - 储物桌 黑色", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/48955200.jpg", "decor://", "2,499", "特惠抢购"),
                MallRecommendItem.RecommendWareModel("SÖDERHAMN 索德汉 - 三人沙发, 芬斯塔 天蓝色", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/77553511.jpg", "decor://", "4,499", "全网最低"),
                MallRecommendItem.WareSubjectModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/24891682.jpg", "decor://"),
                MallRecommendItem.RecommendWareModel("KALANCHOE - 长寿花 盆栽植物", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/95633461.jpg", "decor://", "19.90", null),
                MallRecommendItem.RecommendWareModel("SMAKMÄSSIG 斯玛麦希 - 玻璃水杯 6件", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/19207029.jpg", "decor://", "59.90", null),
                MallRecommendItem.WareSubjectModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/27650299.jpg", "decor://"),
                MallRecommendItem.RecommendWareModel("ChairUnknown 忘记是什么椅子了", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/52477206.jpg", "decor://", "149.00", null),
                MallRecommendItem.RecommendWareModel("Potted 绿油油 - 盆栽植物", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/90543723.jpg", "decor://", "79.90", null)
        )))
    }

    private fun getLocalDailyRecommend(): Single<DailyRecommendModel> {
        return Single.just(DailyRecommendModel(1, listOf(
                RecommendItemModel.DiscoverModel(
                        title = "发现兔狗家装",
                        banners = listOf(
                                HomeBannerModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/90669551.jpg", "decor://A"),
                                HomeBannerModel("image", "decor://A")
                        ),
                        entries = listOf(
                                EntryModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/72054422.jpg", "装修设计", "decor://HomeDesign"),
                                EntryModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/98019651.jpg", "优质好物", "decor://Ware"),
                                EntryModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/92167500.jpg", "挑好货", "decor://Ware")
                        )
                ),
                RecommendItemModel.MagazineListModel(
                        title = "兔狗の家装周刊",
                        magazines = listOf(
                                MagazineModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/5962367.jpg"),
                                MagazineModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/90673214.jpg"),
                                MagazineModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/17023172.jpg"),
                                MagazineModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/5962367.jpg"),
                                MagazineModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/90673214.jpg"),
                                MagazineModel(0, "")
                        )
                ),
                RecommendItemModel.WareListModel(
                        title = "臻选好物推",
                        moreAppUrl = "decor://WareCategory",
                        wares = listOf(
                                RecommendWareModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/48955200.jpg", "GUALÖV 格拉瓦 - 储物桌 黑色", "可用作托盘盛放零食，或是放书籍杂志，不碍事又方便。", "2,499", "特选优惠"),
                                RecommendWareModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/77553511.jpg", "SÖDERHAMN 索德汉 - 三人沙发, 芬斯塔 天蓝色", "厚实的坐垫和可移动靠垫使用透气面料，格外舒适。", "4,499", "全网最低"),
                                RecommendWareModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/95633461.jpg", "KALANCHOE - 长寿花 盆栽植物", "用搭配的盆栽来装饰房间，找到属于你自己的风格。", "19.90", null),
                                RecommendWareModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/19207029.jpg", "SMAKMÄSSIG 斯玛麦希 - 玻璃水杯 6件", "玻璃杯宽大厚实，杯身简洁、低而直，易于抓握。", "59.90", null)
                        )
                ),
                RecommendItemModel.ContentHeaderModel(
                        title = "热门灵感文章 & 美图",
                        tags = listOf(
                                TagModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/40204688.jpg", "decor://", ""),
                                TagModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/86883263.jpg", "decor://", ""),
                                TagModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/2860859.jpg", "decor://", ""),
                                TagModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/52580348.jpg", "decor://", ""),
                                TagModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/22486834.jpg", "decor://", ""),
                                TagModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/89034175.jpg", "decor://", "")
                        )
                ),
                RecommendItemModel.RecommendArticleModel(1, "2019年宜家产品目录：为满足不同人与需求而设计。", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/21922802.jpg", listOf("IKEA", "家具热点")),
                RecommendItemModel.RecommendAlbumModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/96334285.jpg"),
                RecommendItemModel.RecommendArticleModel(1, "让旧房间焕然一新——挂上图片！", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/12441168.jpg", listOf("装饰", "客厅", "实景案例")),
                RecommendItemModel.RecommendArticleModel(1, "2019年宜家产品目录：为满足不同人与需求而设计。", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/21922802.jpg", listOf("IKEA", "家具热点")),
                RecommendItemModel.RecommendAlbumModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-8/10012706.jpg"),
                RecommendItemModel.RecommendArticleModel(1, "让旧房间焕然一新——挂上图片！", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/12441168.jpg", listOf("装饰", "客厅", "实景案例")),
                RecommendItemModel.RecommendArticleModel(1, "2019年宜家产品目录：为满足不同人与需求而设计。", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/21922802.jpg", listOf("IKEA", "家具热点")),
                RecommendItemModel.RecommendAlbumModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/96334285.jpg"),
                RecommendItemModel.RecommendArticleModel(1, "让旧房间焕然一新——挂上图片！", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/12441168.jpg", listOf("装饰", "客厅", "实景案例")),
                RecommendItemModel.RecommendArticleModel(1, "2019年宜家产品目录：为满足不同人与需求而设计。", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/21922802.jpg", listOf("IKEA", "家具热点")),
                RecommendItemModel.RecommendAlbumModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-8/10012706.jpg"),
                RecommendItemModel.RecommendArticleModel(1, "让旧房间焕然一新——挂上图片！", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/12441168.jpg", listOf("装饰", "客厅", "实景案例")),
                RecommendItemModel.RecommendArticleModel(1, "2019年宜家产品目录：为满足不同人与需求而设计。", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/21922802.jpg", listOf("IKEA", "家具热点")),
                RecommendItemModel.RecommendAlbumModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-9/96334285.jpg"),
                RecommendItemModel.RecommendArticleModel(1, "让旧房间焕然一新——挂上图片！", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/12441168.jpg", listOf("装饰", "客厅", "实景案例")),
                RecommendItemModel.RecommendArticleModel(1, "2019年宜家产品目录：为满足不同人与需求而设计。", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/21922802.jpg", listOf("IKEA", "家具热点")),
                RecommendItemModel.RecommendAlbumModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-8/10012706.jpg"),
                RecommendItemModel.RecommendArticleModel(1, "让旧房间焕然一新——挂上图片！", "http://ou4f31a1x.bkt.clouddn.com/18-9-9/12441168.jpg", listOf("装饰", "客厅", "实景案例"))
        )))
    }

    override fun preload() {
    }

    override fun getExploreData(): Single<Any> {
        return Single.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map { Any() }
    }

    override fun getMuseConfig(): Flowable<HomeConfigModel> {
        return homeApi.getMuseConfig()
                .scheduler()
                .handleResponse()
                .mergeWith(getLocalMuseConfig())
    }

    private fun getLocalMuseConfig(): Single<HomeConfigModel>? {
        return Single.just(HomeConfigModel(
                banners = listOf(
                        HomeBannerModel("http://ou4f31a1x.bkt.clouddn.com/18-9-8/38706980.jpg", "decor://WareSubject?id=1"),
                        HomeBannerModel("http://ou4f31a1x.bkt.clouddn.com/18-9-9/53522711.jpg", "decor://WareSubject?id=2"),
                        HomeBannerModel("", "")
                ),
                entries = listOf(
                        EntryModel("http://ou4f31a1x.bkt.clouddn.com/18-9-8/60582397.jpg", "兔狗作品", "decor://TugouWork"),
                        EntryModel("http://ou4f31a1x.bkt.clouddn.com/18-9-8/48560701.jpg", "实景案例", "decor://ArticleList?tag_id=1"),
                        EntryModel("http://ou4f31a1x.bkt.clouddn.com/18-9-8/10744378.jpg", "装修攻略", "decor://ArticleList?tag_id=2"),
                        EntryModel("http://ou4f31a1x.bkt.clouddn.com/18-9-8/44353089.jpg", "灵感图辑", "decor://AlbumList")
                )
        ))
    }

    override fun getInboxOverview(): Single<InboxOverviewModel> {
        TODO("not implemented")
    }

    override fun checkInboxUnreadCount(): Single<Int> {
        TODO("not implemented")
    }

    override fun getMineEntries(): Flowable<List<EntryModel>> {
        val localEntries = listOf(
                EntryModel(R.drawable.ic_my_orders, "我的定单", "decor://OrderList"),
                EntryModel(R.drawable.ic_my_coupons, "我的卡包", "decor://CouponHolder"),
                EntryModel(R.drawable.ic_my_collections, "我的收藏", "decor://MyCollections"),
                EntryModel(R.drawable.ic_my_style_test, "装修测试", "https://tugou.com/decor-test"),
                EntryModel(R.drawable.ic_my_bps_mall, "积分商城", "decor://bp.tugou.com"),
                EntryModel(R.drawable.ic_my_account_book, "装修记账", "decor://ExpenseBook"),
                EntryModel(R.drawable.ic_my_calculator, "计算器", "decor://DecorCalculator"),
                EntryModel(R.drawable.ic_my_schedule, "装修进度", "decor://DecorSchedule")
        )

        return Flowable.just(localEntries)
        /*return homeApi.getMineEntries()
                .handleResponse()
                .toObservable()
                .mergeWith(Observable.just(localEntries))*/
    }

    override fun getRecentActivities(): Single<List<RecentActivityModel>> {
        return Single.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    listOf(
                            RecentActivityModel("促销·当季家居超值臻选，低至3折", "http://ou4f31a1x.bkt.clouddn.com/18-9-6/96372985.jpg", "decor://19000/?subject_id=178"),
                            RecentActivityModel("家装国庆盛典·城中香格里拉大酒店", "http://ou4f31a1x.bkt.clouddn.com/18-9-6/72383753.jpg", "https://tuan.tugou.com/hangzhou/181001"),
                            RecentActivityModel("黑卡会员限时优惠，尽享20项特权", "http://ou4f31a1x.bkt.clouddn.com/18-9-6/25821788.jpg", "https://vip.tugou.com/limit-promotion")
                    )
                }
    }
}