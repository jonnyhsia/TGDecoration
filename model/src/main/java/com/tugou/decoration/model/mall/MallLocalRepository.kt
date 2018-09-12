package com.tugou.decoration.model.mall

import com.tugou.decoration.model.muse.entity.MallRecommendItem
import com.tugou.decoration.model.muse.entity.MallRecommendModel
import io.reactivex.Single

object MallLocalRepository {

    internal fun getLocalMallRecommend(): Single<MallRecommendModel> {
        return Single.just(MallRecommendModel(1, listOf(
                MallRecommendItem.WareCollectionModel("新上架臻品", listOf(
                        MallRecommendItem.WareCollectionModel.WareItemModel("VARIERA 盒子", "节省翻找时间，更多烹饪乐趣", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/58848006.jpg", ""),
                        MallRecommendItem.WareCollectionModel.WareItemModel("FABRIKÖR 门柜 ", "陈列和保护您的玻璃杯和心爱的收藏品。", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/25634404.jpg", ""),
                        MallRecommendItem.WareCollectionModel.WareItemModel("LÖVBACKEN 边桌", "经久耐用，不易弄脏，清洁方便。", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/90427417.jpg", ""),
                        MallRecommendItem.WareCollectionModel.WareItemModel("VARIERA 盒子", "节省翻找时间，更多烹饪乐趣", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/58848006.jpg", ""),
                        MallRecommendItem.WareCollectionModel.WareItemModel("FABRIKÖR 门柜 ", "陈列和保护您的玻璃杯和心爱的收藏品。", "http://ou4f31a1x.bkt.clouddn.com/18-9-10/25634404.jpg", ""),
                        MallRecommendItem.WareCollectionModel.WareItemModel("", "", "", "decor://WareList")
                )),
                MallRecommendItem.ArticleCollectionModel("臻品测评", listOf(
                        MallRecommendItem.ArticleCollectionModel.ArticleItemModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/64410752.jpg", "宜家沙发测评，经典款 VS 夏季新款,哪个更胜一筹?", ""),
                        MallRecommendItem.ArticleCollectionModel.ArticleItemModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/15478349.jpg", "好评一篇的米家扫地机器人，真的有这么好用吗？", ""),
                        MallRecommendItem.ArticleCollectionModel.ArticleItemModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/64410752.jpg", "宜家沙发测评，经典款 VS 夏季新款,哪个更胜一筹?", ""),
                        MallRecommendItem.ArticleCollectionModel.ArticleItemModel("http://ou4f31a1x.bkt.clouddn.com/18-9-10/15478349.jpg", "好评一篇的米家扫地机器人，真的有这么好用吗？", ""),
                        MallRecommendItem.ArticleCollectionModel.ArticleItemModel("", "", "decor://ArticleList?tag=10")
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

}