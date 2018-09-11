package com.tugou.decoration.page.home

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tugou.decoration.R
import com.tugou.decoration.ext.findView
import com.tugou.decoration.ext.load
import com.tugou.decoration.model.home.entity.EntryModel
import com.tugou.decoration.widget.recyclerview.TGItemViewBinder

class EntryViewBinder(
        delegate: HomeEntryDelegate
) : TGItemViewBinder<EntryModel, EntryViewBinder.ViewHolder>(), HomeEntryDelegate by delegate {

    override val itemViewRes = R.layout.item_decor_entry

    override fun onCreateViewHolder(parent: ViewGroup, itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: EntryModel) {
        with(holder) {
            imgEntry.load(item.icon) { simpleOption() }
            tvEntry.text = item.text
            itemView.setOnClickListener { tapEntry(item) }
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgEntry = findView<ImageView>(R.id.imgEntry)
        val tvEntry = findView<TextView>(R.id.tvEntry)
    }
}

interface HomeEntryDelegate {
    /**
     * 点击入口
     */
    fun tapEntry(entry: EntryModel)
}