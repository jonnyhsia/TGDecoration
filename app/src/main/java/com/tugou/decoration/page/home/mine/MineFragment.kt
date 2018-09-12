package com.tugou.decoration.page.home.mine

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.core.TGFragment
import com.tugou.core.getViewModelOf
import com.tugou.decoration.BuildConfig
import com.tugou.decoration.GlideApp
import com.tugou.decoration.R
import com.tugou.decoration.ext.addOnClickListener
import com.tugou.decoration.ext.application
import com.tugou.decoration.widget.recyclerview.SpacingItemDecoration
import com.tugou.decoration.widget.recyclerview.TGAdapter
import kotlinx.android.synthetic.main.fragment_mine.*
import me.drakeet.multitype.Items
import me.drakeet.multitype.register

class MineFragment : TGFragment<MineViewModel>() {
    override val layoutRes = R.layout.fragment_mine

    private val entryAdapter = TGAdapter()
    private val activityAdapter = TGAdapter()

    override fun onCreateViewModel(savedInstanceState: Bundle?) {
        viewModel = getViewModelOf { MineViewModel() }

        viewModel.fetchUserInfo()
        viewModel.fetchMineConfig()

        entryAdapter.register(MineEntryViewBinder(viewModel))
        activityAdapter.register(RecentActivityViewBinder(viewModel))

        viewModel.loginUser.observe(this, Observer {
            if (it == null) {
                groupProfile.visibility = View.GONE
                imgTugouVip.visibility = View.GONE
                return@Observer
            }

            with(it) {
                groupProfile.visibility = View.VISIBLE
                imgTugouVip.visibility = if (isVip) View.VISIBLE else View.GONE

                tvUsername.text = username
                GlideApp.with(imgAvatar)
                        .load(avatar)
                        .commonOption()
                        .into(imgAvatar)
            }
        })

        viewModel.entries.observe(this, Observer {
            entryAdapter.items = Items(it)
            entryAdapter.notifyDataSetChanged()
        })

        viewModel.recentActivities.observe(this, Observer {
            activityAdapter.items = Items(it)
            activityAdapter.notifyDataSetChanged()
            recycleActivity.visibility = View.VISIBLE
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 入口网格
        recycleEntry.setHasFixedSize(true)
        recycleEntry.layoutManager = GridLayoutManager(context, 4)
        recycleEntry.adapter = entryAdapter

        // 活动列表
        recycleActivity.setHasFixedSize(true)
        recycleActivity.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycleActivity.adapter = activityAdapter
        // 设置活动滑动
        val snapHelper = GravityPagerSnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(recycleActivity)
        // 设置活动 Item 间距
        val spacing = application.dp2px(5f).toInt()
        recycleActivity.addItemDecoration(
                SpacingItemDecoration(spacing, 0, spacing, 0).apply {
                    edgeSpacingFactory = 0f
                })

        // 顶部按钮点击
        btnEditProfile.setOnClickListener { viewModel.tapEditProfile() }
        btnRelationship.setOnClickListener { viewModel.tapRelationship() }
        btnSettings.setOnClickListener { viewModel.tapSettings() }

        // Profile 区域点击
        groupProfile.addOnClickListener(View.OnClickListener {
            viewModel.tapProfile()
        })
        imgTugouVip.setOnClickListener { viewModel.tapTugouVip() }

        // 其他 Label 点击
        labelFeedback.labelClicked { viewModel.tapFeedback() }
        labelInviteFriend.labelClicked { viewModel.tapInviteFriend() }
        labelSettings.labelClicked { viewModel.tapSettings() }

        tvAppInfo.text = "兔狗家装 · 探索版 · v${BuildConfig.VERSION_NAME}"
    }
}