package com.cookpad.hiring.android.ui.favouriterecipe

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.cookpad.hiring.android.data.entities.Collection

class FavRecipeAdapter(private val callback: (Collection) -> Unit) :
    ListAdapter<Collection, FavRecipeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavRecipeViewHolder {
        return FavRecipeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FavRecipeViewHolder, position: Int) {
        holder.bind(getItem(position), position) {
            callback.invoke(getItem(it))
            notifyDataSetChanged()
        }
    }

    fun refreshList(favRecipes: MutableList<Collection>?) {
        submitList(favRecipes)
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
