package com.cookpad.hiring.android.data

import com.cookpad.hiring.android.data.api.CookpadHiringService
import com.cookpad.hiring.android.data.dtos.CollectionDTO
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
class CollectionListRepositoryTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val mockCookpadService: CookpadHiringService = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when remote api returns success Then repo should also return success with correct mapping`() {
        val collectionDto = getCollectionsDto()
        runTest {
            whenever(mockCookpadService.getCollections()).thenReturn(collectionDto)
            val collectionListRepository = CollectionListRepository(mockCookpadService)

            val collectionList = collectionListRepository.getCollectionList()

            assertEquals(collectionList.size, collectionDto.size)
            assertEquals(collectionList.first().id, collectionDto.first().id)
        }
    }

    private fun getCollectionsDto(): List<CollectionDTO> {
        return listOf(
            CollectionDTO(
                id = 1,
                title = "title",
                description = "desc",
                recipeCount = 2,
                previewImageUrls = listOf("urls")
            )
        )
    }
}
