package com.cookpad.hiring.android.domain.usecase

import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.domain.repository.IFavouriteRecipeRepo
import javax.inject.Inject


class FavouriteRecipeUseCase @Inject constructor(private val iFavouriteRecipeRepo: IFavouriteRecipeRepo) {
    suspend fun setFavouriteRecipe(id: Int, isFavourite: Boolean): Int =
        iFavouriteRecipeRepo.setFavouriteRecipe(id, isFavourite)
    suspend fun getFavouriteRecipe(): List<Collection> = iFavouriteRecipeRepo.getFavouriteRecipe()
}