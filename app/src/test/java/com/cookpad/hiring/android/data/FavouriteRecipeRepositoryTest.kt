package com.cookpad.hiring.android.data

import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.data.repository.FavouriteRecipeRepository
import com.cookpad.hiring.android.data.room.RecipeDao
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
class FavouriteRecipeRepositoryTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val mockDao: RecipeDao = mock()

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
            whenever(mockDao.getFavouriteRecipes()).thenReturn(collection)
            val favouriteRecipeRepository = FavouriteRecipeRepository(mockDao)
            val collectionList = favouriteRecipeRepository.getFavouriteRecipe()
            assertEquals(collectionList.size, 1)
            assertEquals(collectionList.first().id, 1)
        }
    }

    @Test
    fun `check success of set favourite recipe to local db`() {
        runTest {
            whenever(mockDao.setFavourite(any(), any())).thenReturn(1)
            val favouriteRecipeRepository = FavouriteRecipeRepository(mockDao)
            val result = favouriteRecipeRepository.setFavouriteRecipe(1, true)
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
