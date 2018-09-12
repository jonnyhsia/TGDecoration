package com.tugou.decoration.model.muse

import com.tugou.decoration.model.base.TGRepository
import com.tugou.decoration.model.home.entity.DecorProgressTipsModel
import com.tugou.decoration.model.home.entity.DecorTipModel
import com.tugou.decoration.model.muse.entity.*
import io.reactivex.Single

internal object MuseLocalRepository : TGRepository() {

    internal fun getLocalMuseTimeline(): Single<MuseTimelineModel> {
        val author = AuthorModel(0, "高能的土豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-6/61589954.jpg", "介绍介绍", false)
        val behavior = MuseBehavior(127, 1024, false, true)

        return Single.just(MuseTimelineModel(1, 1, listOf(
                MuseModel.TipModel("", listOf(
                        DecorProgressTipsModel("装修前", listOf(
                                DecorTipModel("decor://ArticleList?tag_id=10", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/55096255.jpg"),
                                DecorTipModel("decor://ArticleList?tag_id=11", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/92798090.jpg"),
                                DecorTipModel("decor://ArticleList?tag_id=12", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/47584544.jpg"),
                                DecorTipModel("decor://ArticleList?tag_id=13", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/78919753.jpg"),
                                DecorTipModel("decor://ArticleList?tag_id=14", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/29678719.jpg")
                        )),
                        DecorProgressTipsModel("装修中", listOf(
                                DecorTipModel("image", ""),
                                DecorTipModel("image", ""),
                                DecorTipModel("image", ""),
                                DecorTipModel("image", ""),
                                DecorTipModel("image", ""),
                                DecorTipModel("image", "")
                        )),
                        DecorProgressTipsModel("装修后", listOf(
                                DecorTipModel("image", ""),
                                DecorTipModel("image", ""),
                                DecorTipModel("image", ""),
                                DecorTipModel("image", ""),
                                DecorTipModel("image", ""),
                                DecorTipModel("image", "")
                        ))
                )),
                MuseModel.ArticleModel(1, "家装参观·在都市生活中，布置一个宁静而紧凑的家", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/81838808.jpg", author, "9月1日", "随便推荐冇理由", behavior),
                MuseModel.RecommendUserListModel("推荐家装UP主", listOf(
                        RecommendUserModel(1, "高能的土豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/55604282.jpg", "家装新秀", null),
                        RecommendUserModel(1, "哈利波豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-6/61589954.jpg", "家装新秀", "3000次收藏"),
                        RecommendUserModel(1, "米开朗基豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/93814703.jpg", "家装新秀", "值得关注的UP主"),
                        RecommendUserModel(1, "马赛克的豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/40108420.jpg", "家装新秀", "测评专家"),
                        RecommendUserModel(1, "什么豆", "", "家装新秀", null),
                        RecommendUserModel(1, "想不出名字豆", "", "家装新秀", "100次发布")
                )),
                MuseModel.AlbumModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-8/10012706.jpg", author, "9月1日", "随便推荐冇理由", behavior),
                MuseModel.SinglePictureModel(1, "运用色彩可以立即为家里增添亮色。或者搭建桥梁。将墙面刷成介于两种颜色之间的第三种颜色，将二者联系起来。\n色彩非常有助于营造和谐统一的外观。但我们要做的还有很多。材料的外观和质感也非常重要。将哑光表面和亮面搭配在一起，比如花瓣和 ARV 阿尔弗 深盘，就可以营造美丽的对比效果。", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/25334353.jpg", 1.25f, author, "8月32日", "没什么理由", behavior),
                MuseModel.ArticleModel(1, "家装参观·在都市生活中，布置一个宁静而紧凑的家", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/81838808.jpg", author, "9月1日", "随便推荐冇理由", behavior),
                MuseModel.RecommendUserListModel("推荐家装UP主", listOf(
                        RecommendUserModel(1, "高能的土豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/55604282.jpg", "家装新秀", null),
                        RecommendUserModel(1, "哈利波豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-6/61589954.jpg", "家装新秀", "3000次收藏"),
                        RecommendUserModel(1, "米开朗基豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/93814703.jpg", "家装新秀", "值得关注的UP主"),
                        RecommendUserModel(1, "马赛克的豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/40108420.jpg", "家装新秀", "测评专家"),
                        RecommendUserModel(1, "什么豆", "", "家装新秀", null),
                        RecommendUserModel(1, "想不出名字豆", "", "家装新秀", "100次发布")
                )),
                MuseModel.AlbumModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-8/10012706.jpg", author, "9月1日", "随便推荐冇理由", behavior),
                MuseModel.SinglePictureModel(1, "运用色彩可以立即为家里增添亮色。或者搭建桥梁。将墙面刷成介于两种颜色之间的第三种颜色，将二者联系起来。\n色彩非常有助于营造和谐统一的外观。但我们要做的还有很多。材料的外观和质感也非常重要。将哑光表面和亮面搭配在一起，比如花瓣和 ARV 阿尔弗 深盘，就可以营造美丽的对比效果。", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/25334353.jpg", 1.25f, author, "8月32日", "没什么理由", behavior),
                MuseModel.ArticleModel(1, "家装参观·在都市生活中，布置一个宁静而紧凑的家", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/81838808.jpg", author, "9月1日", "随便推荐冇理由", behavior),
                MuseModel.RecommendUserListModel("推荐家装UP主", listOf(
                        RecommendUserModel(1, "高能的土豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/55604282.jpg", "家装新秀", null),
                        RecommendUserModel(1, "哈利波豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-6/61589954.jpg", "家装新秀", "3000次收藏"),
                        RecommendUserModel(1, "米开朗基豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/93814703.jpg", "家装新秀", "值得关注的UP主"),
                        RecommendUserModel(1, "马赛克的豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/40108420.jpg", "家装新秀", "测评专家"),
                        RecommendUserModel(1, "什么豆", "", "家装新秀", null),
                        RecommendUserModel(1, "想不出名字豆", "", "家装新秀", "100次发布")
                )),
                MuseModel.AlbumModel(1, "http://ou4f31a1x.bkt.clouddn.com/18-9-8/10012706.jpg", author, "9月1日", "随便推荐冇理由", behavior),
                MuseModel.SinglePictureModel(1, "运用色彩可以立即为家里增添亮色。或者搭建桥梁。将墙面刷成介于两种颜色之间的第三种颜色，将二者联系起来。\n色彩非常有助于营造和谐统一的外观。但我们要做的还有很多。材料的外观和质感也非常重要。将哑光表面和亮面搭配在一起，比如花瓣和 ARV 阿尔弗 深盘，就可以营造美丽的对比效果。", "http://ou4f31a1x.bkt.clouddn.com/18-9-8/25334353.jpg", 1.25f, author, "8月32日", "没什么理由", behavior)
        )))
    }
}