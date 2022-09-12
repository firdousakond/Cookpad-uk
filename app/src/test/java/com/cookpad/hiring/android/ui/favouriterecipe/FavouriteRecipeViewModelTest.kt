package com.cookpad.hiring.android.ui.favouriterecipe

import com.cookpad.hiring.android.data.Resource
import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.domain.usecase.FavouriteRecipeUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class FavouriteRecipeViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var favouriteRecipeViewModel: FavRecipeViewModel
    private val mockFavouriteRecipeUseCase: FavouriteRecipeUseCase = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given favourite recipes are loaded When local data source is success Then emit success view state`() {
        val expectedCollections = getCollections()
        runTest {
            whenever(mockFavouriteRecipeUseCase.getFavouriteRecipe()).thenReturn(expectedCollections)
            favouriteRecipeViewModel = FavRecipeViewModel(mockFavouriteRecipeUseCase)
            val job = launch {
                favouriteRecipeViewModel.favViewState.collectLatest {
                    delay(2000)
                    assertEquals(it,  Resource.Success(expectedCollections))
                }
            }
            job.cancel()
        }
    }

    @Test
    fun `Given favourite recipes are loaded When local data source is error Then emit error view state`() {
        runTest {
            whenever(mockFavouriteRecipeUseCase.getFavouriteRecipe()).thenThrow(RuntimeException(""))
            favouriteRecipeViewModel = FavRecipeViewModel(mockFavouriteRecipeUseCase)

            val job = launch {
                favouriteRecipeViewModel.favViewState.collectLatest {
                    delay(2000)
                    assertEquals(it, Resource.Error)
                }
            }
            job.cancel()
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