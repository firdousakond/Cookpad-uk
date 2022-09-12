package com.cookpad.hiring.android.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cookpad.hiring.android.R

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(imageView)
    }else{
        imageView.setImageResource(R.drawable.ic_placeholder)
    }
}

@BindingAdapter("favouriteImage")
fun setFavouriteImage(imageView: ImageView, isFavourite: Boolean) {
    if (isFavourite) {
        imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
    } else {
        imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }
}

@BindingAdapter("recipeCount")
fun totalRecipeCount(textView: TextView, recipeCount: Int) {
    val count = textView.context.getString(
        R.string.collection_recipe_count,
        recipeCount.toString()
    )
    textView.text = count
}