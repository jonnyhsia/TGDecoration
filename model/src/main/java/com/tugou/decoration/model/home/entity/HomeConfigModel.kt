package com.tugou.decoration.model.home.entity

data class HomeConfigModel(
        val banners: List<HomeBannerModel>,
        val entries: List<EntryModel>
)

data class MineConfigModel(
        val entries: List<EntryModel>,
        val recentActivities: List<RecentActivityModel>
)

data class HomeBannerModel(
        val image: String,
        val destination: String
)

data class DecorProgressTipsModel(
        val progress: String,
        val tips: List<DecorTipModel>
)

data class DecorTipModel(
        val destination: String,
        val image: String
)