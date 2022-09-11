package com.cookpad.hiring.android.ui.recipecollection

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.cookpad.hiring.android.data.entities.Collection

class CollectionListAdapter(private val callback: (Collection)->Unit) : ListAdapter<Collection, CollectionListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionListViewHolder {
        return CollectionListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CollectionListViewHolder, position: Int) {
        holder.bind(getItem(position), position){
            callback.invoke(getItem(it))
            getItem(it).favourite = getItem(it).favourite.not()
            notifyItemChanged(it)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Collection>() {
            override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean {
                return oldItem == newItem
            }
        }
    }
}
