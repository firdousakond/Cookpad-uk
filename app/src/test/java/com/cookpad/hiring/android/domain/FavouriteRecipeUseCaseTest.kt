package com.cookpad.hiring.android.domain

import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.domain.repository.IFavouriteRecipeRepo
import com.cookpad.hiring.android.domain.usecase.FavouriteRecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class FavouriteRecipeUseCaseTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val iFavouriteRecipeRepo: IFavouriteRecipeRepo = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `check success of favourite recipe from local db`() {
        val collection = getCollections()
        runTest {
            whenever(iFavouriteRecipeRepo.getFavouriteRecipe()).thenReturn(collection)
            val favouriteRecipeUseCase = FavouriteRecipeUseCase(iFavouriteRecipeRepo)
            val collectionList = favouriteRecipeUseCase.getFavouriteRecipe()
            assertEquals(collectionList.size, 1)
            assertEquals(collectionList.first().id, 1)
        }
    }

    @Test
    fun `check success of set favourite recipe to local db`() {
        runTest {
            whenever(iFavouriteRecipeRepo.setFavouriteRecipe(any(), any())).thenReturn(1)
            val favouriteRecipeUseCase = FavouriteRecipeUseCase(iFavouriteRecipeRepo)
            val result = favouriteRecipeUseCase.setFavouriteRecipe(1, true)
            assertEquals(result, 1)
        }
    }

    private fun getCollections(): List<Collection> {
        val element = Collection(
            id = 1,
            title = "title",
            description = "disc",
            recipeCount = 3,
            previewImageUrls = listOf("urls"),
            favourite = false
        )
        return listOf(element)
    }
}
