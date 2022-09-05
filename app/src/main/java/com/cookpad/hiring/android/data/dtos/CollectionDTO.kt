package com.cookpad.hiring.android.data.dtos

import com.squareup.moshi.Json

data class CollectionDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "recipe_count") val recipeCount: Int,
    @Json(name = "preview_image_urls") val previewImageUrls: List<String>
)