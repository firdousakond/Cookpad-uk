package com.cookpad.hiring.android.domain.repository

import com.cookpad.hiring.android.data.entities.Collection

interface IFavouriteRecipeRepo {
    suspend fun setFavouriteRecipe(id: Int, isFavourite: Boolean) : Int
    suspend fun getFavouriteRecipe(): List<Collection>
}