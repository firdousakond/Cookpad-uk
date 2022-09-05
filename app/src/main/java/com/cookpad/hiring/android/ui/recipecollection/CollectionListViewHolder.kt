package com.cookpad.hiring.android.ui.recipecollection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cookpad.hiring.android.R
import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.databinding.CollectionListItemBinding

class CollectionListViewHolder(private val binding: CollectionListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Collection) {
        Glide.with(binding.root.context)
            .load(item.previewImageUrls.firstOrNull())
            .centerCrop()
            .into(binding.CollectionImageView)

        binding.collectionNameTextView.text = item.title
        binding.descriptionTextView.text = item.description
        binding.RecipeCountTextView.text = binding.root.context.getString(
            R.string.collection_recipe_count,
            item.recipeCount.toString()
        )
    }

    companion object {
        fun create(parent: ViewGroup): CollectionListViewHolder {
            val restaurantListItemBinding =
                CollectionListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return CollectionListViewHolder(restaurantListItemBinding)
        }
    }
}