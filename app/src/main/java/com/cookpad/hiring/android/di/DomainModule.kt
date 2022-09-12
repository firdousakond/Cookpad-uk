package com.cookpad.hiring.android.di

import com.cookpad.hiring.android.data.repository.CollectionListRepository
import com.cookpad.hiring.android.data.repository.FavCollectionRepository
import com.cookpad.hiring.android.domain.repository.ICollectionListRepo
import com.cookpad.hiring.android.domain.repository.IFavouriteRecipeRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindCollectionListRepository(collectionListRepository: CollectionListRepository): ICollectionListRepo

    @Binds
    abstract fun bindFavouriteRecipeRepository(favCollectionRepository: FavCollectionRepository): IFavouriteRecipeRepo

}