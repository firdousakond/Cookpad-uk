package com.cookpad.hiring.android.data.api

import com.cookpad.hiring.android.data.dtos.CollectionDTO
import retrofit2.http.GET

interface CookpadHiringService {

    @GET("api/collections/")
    suspend fun getCollections(): List<CollectionDTO>

}