package com.cookpad.hiring.android.ui.favouriterecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cookpad.hiring.android.R
import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.databinding.CollectionListItemBinding

class FavRecipeViewHolder(private val binding: CollectionListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Collection, position: Int, callback: (Int) -> Unit) {
        Glide.with(binding.root.context)
            .load(item.previewImageUrls.firstOrNull())
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_image_24)
            .centerCrop()
            .into(binding.CollectionImageView)

        binding.collectionNameTextView.text = item.title
        binding.descriptionTextView.text = item.description
        binding.RecipeCountTextView.text = binding.root.context.getString(
            R.string.collection_recipe_count,
            item.recipeCount.toString()
        )
        binding.favouriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24)
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