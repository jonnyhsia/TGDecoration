package com.tugou.decoration.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * 简单的 Adapter
 */
abstract class SimpleAdapter<T>(
        private val layoutRes: Int,
        private var adapterData: List<T> = emptyList()
) : RecyclerView.Adapter<SimpleViewHolder>() {

    private var itemClickListener: ItemTap? = null

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        val viewHolder = SimpleViewHolder(view)
        onViewHolderCreated(viewHolder)
        return viewHolder
    }

    /**
     * 当 ViewHolder 创建完后
     */
    open fun onViewHolderCreated(holder: SimpleViewHolder) {
    }

    final override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        itemClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener(holder.adapterPosition)
            }
        }
        onBindViewHolder(holder, adapterData[position])
    }

    abstract fun onBindViewHolder(holder: SimpleViewHolder, item: T)

    override fun getItemCount() = adapterData.size

    fun setNewData(data: List<T>) {
        adapterData = ArrayList(data)
        notifyDataSetChanged()
    }

    /**
     * 设置 Item 的点击监听
     */
    fun setOnItemClickListener(listener: ItemTap) {
        itemClickListener = listener
    }
}

/**
 * Adapter 对应的 ViewHolder
 */
class SimpleViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)