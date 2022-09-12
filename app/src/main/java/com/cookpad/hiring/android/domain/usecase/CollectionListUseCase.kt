package com.cookpad.hiring.android.domain.usecase

import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.domain.repository.ICollectionListRepo
import javax.inject.Inject

class CollectionListUseCase @Inject constructor (private val iCollectionListRepo: ICollectionListRepo) {
    suspend fun getCollectionList() : List<Collection> = iCollectionListRepo.getCollectionList()
}