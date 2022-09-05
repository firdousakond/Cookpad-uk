package com.cookpad.hiring.android.data

import com.cookpad.hiring.android.data.api.CookpadHiringService
import com.cookpad.hiring.android.data.entities.Collection
import javax.inject.Inject


class CollectionListRepository @Inject constructor(private val cookpadService: CookpadHiringService) {

    suspend fun getCollectionList(): List<Collection> {
        return cookpadService.getCollections().map { collectionDTO ->
            with(collectionDTO) {
                Collection(
                    id = id,
                    title = title,
                    description = description,
                    recipeCount = recipeCount,
                    previewImageUrls = previewImageUrls
                )
            }
        }
    }
}