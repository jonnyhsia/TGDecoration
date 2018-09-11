package com.tugou.decoration.page.home.recommend

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugou.core.TGFragment
import com.tugou.core.getViewModel
import com.tugou.core.observe
import com.tugou.decoration.R
import com.tugou.decoration.page.home.recommend.adapter.*
import com.tugou.decoration.widget.recyclerview.TGAdapter
import kotlinx.android.synthetic.main.fragment_recommend.*
import me.drakeet.multitype.register

class RecommendFragment : TGFragment<RecommendViewModel>() {

    override val layoutRes = R.layout.fragment_recommend

    private val recommendAdapter = TGAdapter()

    override fun onCreateViewModel(savedInstanceState: Bundle?) {
        viewModel = getViewModel()
        viewModel.fetchExploreData()

        recommendAdapter.apply {
            register(DiscoverViewBinder(viewModel))
            register(MagazinesViewBinder(viewModel))
            register(WareListViewBinder(viewModel))
            register(ContentHeaderViewBinder(viewModel))
            register(RecommendArticleViewBinder(viewModel))
            register(RecommendAlbumViewBinder(viewModel))
        }

        viewModel.dailyRecommend.observe(this) {
            it ?: return@observe
            if (it.page == 1) {
                recommendAdapter.setNewData(it.list)
            } else {
                // TODO: 分页加载
                // recommendAdapter.addNewData(it.list.subList())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvWelcomeText.text = "兔狗祝陈星辰小朋友\n\uD83E\uDD27感冒早日康复"
        recycleRecommend.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = recommendAdapter
        }
    }
}