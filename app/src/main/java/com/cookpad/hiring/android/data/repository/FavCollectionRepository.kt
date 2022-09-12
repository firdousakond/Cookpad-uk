package com.cookpad.hiring.android.data.repository

import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.data.room.RecipeDao
import com.cookpad.hiring.android.domain.repository.IFavouriteRecipeRepo
import javax.inject.Inject

class FavCollectionRepository @Inject constructor(private val recipeDao: RecipeDao) :
    IFavouriteRecipeRepo {

    override suspend fun setFavouriteRecipe(id: Int, isFavourite: Boolean): Int =
        recipeDao.setFavourite(id, isFavourite)

    override suspend fun getFavouriteRecipe(): List<Collection> = recipeDao.getFavouriteRecipes()

}