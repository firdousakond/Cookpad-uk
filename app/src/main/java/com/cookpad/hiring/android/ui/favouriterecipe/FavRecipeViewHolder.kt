package com.cookpad.hiring.android.ui.favouriterecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.databinding.CollectionListItemBinding

class FavRecipeViewHolder(private val binding: CollectionListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Collection, position: Int, callback: (Int) -> Unit) {
        binding.collection = item
        binding.favouriteImageView.setOnClickListener {
            callback.invoke(position)
        }
    }

    companion object {
        fun create(parent: ViewGroup): FavRecipeViewHolder {
            val restaurantListItemBinding =
                CollectionListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return FavRecipeViewHolder(restaurantListItemBinding)
        }
    }
}