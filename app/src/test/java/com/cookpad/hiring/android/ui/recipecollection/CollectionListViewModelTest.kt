package com.cookpad.hiring.android.ui.recipecollection

import com.cookpad.hiring.android.data.CollectionListRepository
import com.cookpad.hiring.android.data.entities.Collection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
    private val mockCollectionListRepository: CollectionListRepository = mock()

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
            whenever(mockCollectionListRepository.getCollectionList()).thenReturn(expectedCollections)
            collectionListViewModel = CollectionListViewModel(mockCollectionListRepository)

            val stateFlow = collectionListViewModel.viewState.first()

            assertEquals(stateFlow, CollectionListViewState.Success(expectedCollections))
        }
    }

    @Test
    fun `Given collections are loaded When data source is error Then emit error view state`() {
        runTest {
            whenever(mockCollectionListRepository.getCollectionList()).thenThrow(RuntimeException(""))
            collectionListViewModel = CollectionListViewModel(mockCollectionListRepository)

            val stateFlow = collectionListViewModel.viewState.first()

            assertEquals(stateFlow, CollectionListViewState.Error)
        }
    }

    private fun getCollections(): List<Collection> {
        val element = Collection(
            id = 1,
            title = "title",
            description = "disc",
            recipeCount = 3,
            previewImageUrls = listOf("urls")
        )
        return listOf(element)
    }
}