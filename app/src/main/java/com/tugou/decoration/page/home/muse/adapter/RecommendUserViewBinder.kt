package com.tugou.decoration.page.home.muse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.decoration.R
import com.tugou.decoration.ext.asHorizontalList
import com.tugou.decoration.ext.findView
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.muse.entity.MuseModel
import com.tugou.decoration.model.muse.entity.RecommendUserModel
import com.tugou.decoration.page.home.muse.RecommendUserDelegate
import com.tugou.decoration.widget.recyclerview.SpacingItemDecoration
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

class RecommendUserViewBinder(
        delegate: RecommendUserDelegate
) : TGItemViewBinder<MuseModel.RecommendUserListModel, RecommendUserViewBinder.ViewHolder>(), RecommendUserDelegate by delegate {

    override val itemViewRes = R.layout.item_muse_recommend_user

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, recommendUserList: MuseModel.RecommendUserListModel) {
        with(holder) {
            tvSectionTitle.text = recommendUserList.title
            recycleRecommendUser.adapter = UserAdapter(recommendUserList.userList)
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSectionTitle = findView<TextView>(R.id.tvSectionTitle)
        val recycleRecommendUser = findView<RecyclerView>(R.id.recycleUsers).apply {
            asHorizontalList()
            addItemDecoration(SpacingItemDecoration(context.dp2px(6f).toInt(), 0).apply {
                edgeSpacingFactory = (20f / 6f)
            })
        }
    }

    inner class UserAdapter(adapterData: List<RecommendUserModel>) : RecyclerView.Adapter<UserViewHolder>() {

        private val adapterData = ArrayList(adapterData)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_muse_user, parent, false)
            return UserViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val model = adapterData[position]
            with(holder) {
                imgAvatar.load(model.avatar) { asAvatar() }
                tvUsername.text = model.name
                tvUserRank.text = model.recommendReason ?: model.rank
                itemView.setOnClickListener { tapRecommendUser(adapterData[position]) }
            }
        }

        fun setUsers(data: List<RecommendUserModel>) {
            adapterData.clear()
            adapterData.addAll(data)
            notifyDataSetChanged()
        }

        override fun getItemCount() = adapterData.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvatar = findView<ImageView>(R.id.imgAvatar)
        val tvUsername = findView<TextView>(R.id.tvUsername)
        val tvUserRank = findView<TextView>(R.id.tvUserRank)
    }
}