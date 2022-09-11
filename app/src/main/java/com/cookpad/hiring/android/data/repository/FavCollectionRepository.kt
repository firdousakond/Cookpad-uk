package com.cookpad.hiring.android.data.repository

import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.data.room.RecipeDao
import javax.inject.Inject

class FavCollectionRepository @Inject constructor(private val recipeDao: RecipeDao) {

    suspend fun setFavouriteRecipe(id: Int, isFavourite: Boolean): Int =
        recipeDao.setFavourite(id, isFavourite)

    suspend fun getFavouriteRecipe(): List<Collection> = recipeDao.getFavouriteRecipes()

}