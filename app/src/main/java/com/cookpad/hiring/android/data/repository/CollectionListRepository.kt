package com.cookpad.hiring.android.data.repository

import com.cookpad.hiring.android.data.api.CookpadHiringService
import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.data.room.RecipeDao
import javax.inject.Inject

class CollectionListRepository @Inject constructor(
    private val cookpadService: CookpadHiringService, private val dao: RecipeDao) {

    suspend fun getCollectionList(): List<Collection> {
        val recipes =  cookpadService.getCollections().map { collectionDTO ->
            with(collectionDTO) {
                Collection(
                    id = id,
                    title = title,
                    description = description,
                    recipeCount = recipeCount,
                    previewImageUrls = previewImageUrls,
                    favourite = false
                )
            }
        }
        dao.insertRecipes(recipes)
        return dao.getRecipes()
    }
}