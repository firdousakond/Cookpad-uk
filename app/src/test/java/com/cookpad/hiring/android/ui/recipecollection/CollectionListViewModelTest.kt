package com.cookpad.hiring.android.ui.recipecollection

import com.cookpad.hiring.android.data.Resource
import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.domain.usecase.CollectionListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
class CollectionListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var collectionListViewModel: CollectionListViewModel
    private val mockCollectionListUseCase: CollectionListUseCase = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given collections are loaded When data source is success Then emit success view state`() {
        val expectedCollections = getCollections()
        runTest {
            whenever(mockCollectionListUseCase.getCollectionList()).thenReturn(expectedCollections)
            collectionListViewModel = CollectionListViewModel(mockCollectionListUseCase)
            val job = launch {
                collectionListViewModel.viewState.collectLatest {
                    delay(2000)
                    assertEquals(it,  Resource.Success(expectedCollections))
                }
            }
            job.cancel()
        }
    }

    @Test
    fun `Given collections are loaded When data source is error Then emit error view state`() {
        runTest {
            whenever(mockCollectionListUseCase.getCollectionList()).thenThrow(RuntimeException(""))
            collectionListViewModel = CollectionListViewModel(mockCollectionListUseCase)

            val job = launch {
                collectionListViewModel.viewState.collectLatest {
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