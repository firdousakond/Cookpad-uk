package com.cookpad.hiring.android.data.repository

import android.content.Context
import com.cookpad.hiring.android.data.api.CookpadHiringService
import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.data.room.RecipeDao
import com.cookpad.hiring.android.domain.repository.ICollectionListRepo
import com.cookpad.hiring.android.util.NetworkUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CollectionListRepository @Inject constructor(
    private val cookpadService: CookpadHiringService, private val dao: RecipeDao, @ApplicationContext private val context: Context) : ICollectionListRepo{

   override suspend fun getCollectionList(): List<Collection> {
       if(NetworkUtil.isInternetConnected(context)) {
           val recipes = cookpadService.getCollections().map { collectionDTO ->
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
       }
       return dao.getRecipes()
    }
}