package com.cookpad.hiring.android.domain

import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.domain.repository.ICollectionListRepo
import com.cookpad.hiring.android.domain.usecase.CollectionListUseCase
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
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CollectionListUseCaseTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val iCollectionListRepo: ICollectionListRepo = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when remote api returns success Then use case should also return success`() {
        val collection = getCollections()
        runTest {
            whenever(iCollectionListRepo.getCollectionList()).thenReturn(collection)
            val collectionListUseCase = CollectionListUseCase(iCollectionListRepo)
            val collectionList = collectionListUseCase.getCollectionList()
            assertEquals(collectionList.size, collection.size)
            assertEquals(collectionList.first().id, collection.first().id)
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
