package com.cookpad.hiring.android.di

import android.content.Context
import androidx.room.Room
import com.cookpad.hiring.android.data.room.RecipeDao
import com.cookpad.hiring.android.data.room.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule
{

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext app: Context
    ) : RecipeDatabase = Room.databaseBuilder(
        app,
        RecipeDatabase::class.java,
        "recipe-collection.db"
    )
        .build()

    @Singleton
    @Provides
    fun provideRecipeDao(recipeDatabase: RecipeDatabase): RecipeDao {
        return recipeDatabase.recipeDao()
    }
}