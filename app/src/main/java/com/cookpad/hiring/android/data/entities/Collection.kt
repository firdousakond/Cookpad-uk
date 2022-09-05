package com.cookpad.hiring.android.data.entities

data class Collection(
    val id: Int,
    val title: String,
    val description: String,
    val recipeCount: Int,
    val previewImageUrls: List<String> = emptyList()
)