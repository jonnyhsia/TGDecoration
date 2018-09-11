package com.tugou.decoration.page.home.mine

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tugou.decoration.GlideApp
import com.tugou.decoration.R
import com.tugou.decoration.ext.context
import com.tugou.decoration.ext.findView
import com.tugou.decoration.model.home.entity.EntryModel
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

interface MineEntryDelegate {
    /**
     * 点击入口
     */
    fun tapEntry(entry: EntryModel)
}

class MineEntryViewBinder(
        delegate: MineEntryDelegate
) : TGItemViewBinder<EntryModel, MineEntryViewBinder.ViewHolder>(), MineEntryDelegate by delegate {

    override val itemViewRes = R.layout.item_mine_entry

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, entry: EntryModel) {
        with(holder) {
            GlideApp.with(holder.context)
                    .load(entry.icon)
                    .commonOption()
                    .into(imgEntry)
            tvEntry.text = entry.text
            itemView.setOnClickListener {
                tapEntry(entry)
            }
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgEntry = findView<ImageView>(R.id.imgEntry)
        val tvEntry = findView<TextView>(R.id.tvEntry)
    }
}
