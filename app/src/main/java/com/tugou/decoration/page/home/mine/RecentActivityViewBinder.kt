package com.tugou.decoration.page.home.mine

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tugou.decoration.GlideApp
import com.tugou.decoration.R
import com.tugou.decoration.ext.findView
import com.tugou.decoration.model.home.entity.RecentActivityModel
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

interface RecentActivityDelegate {
    /**
     * 点击最近活动
     */
    fun tapRecentActivity(activity: RecentActivityModel)
}

class RecentActivityViewBinder(
        delegate: RecentActivityDelegate
) : TGItemViewBinder<RecentActivityModel, RecentActivityViewBinder.ViewHolder>(), RecentActivityDelegate by delegate {

    override val itemViewRes = R.layout.item_mine_activity

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, recentActivity: RecentActivityModel) {
        with(holder) {
            tvActivity.text = recentActivity.title

            GlideApp.with(imgActivity)
                    .load(recentActivity.imageUrl)
                    .commonOption()
                    .into(imgActivity)

            itemView.setOnClickListener {
                tapRecentActivity(recentActivity)
            }
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvActivity = findView<TextView>(R.id.tvActivity)
        val imgActivity = findView<ImageView>(R.id.imgActivity)
    }
}
