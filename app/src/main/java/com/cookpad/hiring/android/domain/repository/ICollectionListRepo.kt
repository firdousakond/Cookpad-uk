package com.cookpad.hiring.android.domain.repository

import com.cookpad.hiring.android.data.entities.Collection

interface ICollectionListRepo {
    suspend fun getCollectionList() : List<Collection>
}