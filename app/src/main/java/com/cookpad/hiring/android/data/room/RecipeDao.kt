package com.cookpad.hiring.android.data.room

import androidx.room.*
import com.cookpad.hiring.android.data.entities.Collection

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipeCollection")
    fun getRecipes(): List<Collection>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipes(recipes: List<Collection>): Array<Long>
    @Query("UPDATE recipeCollection set favourite = :isFavourite where id=:id")
    suspend fun setFavourite(id: Int, isFavourite: Boolean) : Int
    @Query("SELECT * FROM recipeCollection where favourite = 1")
    fun getFavouriteRecipes(): List<Collection>
}