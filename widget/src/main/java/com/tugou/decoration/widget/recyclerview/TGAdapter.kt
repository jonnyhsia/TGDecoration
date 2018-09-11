package com.tugou.decoration.widget.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.drakeet.multitype.ItemViewBinder
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

class TGAdapter : MultiTypeAdapter() {

    fun setNewData(data: List<Any>) {
        items = Items(data)
        notifyDataSetChanged()
    }

    fun addNewData(data: List<Any>) {
        val start = items.size
        items = Items(items).apply {
            addAll(data)
        }
        notifyItemRangeInserted(start, data.size)
    }
}

abstract class TGItemViewBinder<T, VH : RecyclerView.ViewHolder> : ItemViewBinder<T, VH>() {

    abstract val itemViewRes: Int

    final override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        val view = inflater.inflate(itemViewRes, parent, false)
        return onCreateViewHolder(parent, view)
    }

    abstract fun onCreateViewHolder(parent: ViewGroup, itemView: View): VH

}