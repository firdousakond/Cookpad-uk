package com.cookpad.hiring.android.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipeCollection")
data class Collection(
    @PrimaryKey
    @NonNull
    val id: Int,
    val title: String,
    val description: String,
    val recipeCount: Int,
    val previewImageUrls: List<String> = emptyList()
)