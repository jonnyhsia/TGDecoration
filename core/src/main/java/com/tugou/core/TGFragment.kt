package com.tugou.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tugou.router.TGRouter

abstract class TGFragment<T : TGViewModel> : Fragment() {

    /** Fragment 主 ViewModel */
    protected lateinit var viewModel: T

    abstract val layoutRes: Int

    /** 创建 ViewModel */
    abstract fun onCreateViewModel(savedInstanceState: Bundle?)

    final override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    final override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onCreateViewModel(savedInstanceState)

        // 观察 response 的结果
        viewModel.response.observe(this, Observer {
            when (it) {
                is RequestState.StateFetching -> {
                    showLoadingIndicator()
                }
                is RequestState.StateSuccess -> {
                    hideLoadingIndicator()
                    hideNetworkError()
                }
                is RequestState.StateFailed -> {
                    // 显示错误信息
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    hideLoadingIndicator()
                }
                is RequestState.StateNetworkError -> {
                    showNetworkError()
                    hideLoadingIndicator()
                }
            }
        })

        viewModel.messageHub.observe(this, Observer {
            when (it) {
                is HubMessage.DisplayMsg -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
                is HubMessage.RouteMsg -> {
                    TGRouter.navigate(context!!, it.url)
                }
            }
        })
    }

    /**
     * TODO: 显示加载指示器
     */
    private fun showLoadingIndicator() {
    }

    /**
     * TODO: 隐藏加载指示器
     */
    private fun hideLoadingIndicator() {
    }

    /**
     * TODO: 显示网络错误
     */
    private fun showNetworkError() {
    }

    /**
     * TODO: 隐藏网络错误
     */
    private fun hideNetworkError() {
    }

}