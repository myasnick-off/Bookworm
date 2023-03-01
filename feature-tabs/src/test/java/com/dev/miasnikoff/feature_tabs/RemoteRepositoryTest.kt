package com.dev.miasnikoff.feature_tabs

import com.dev.miasnikoff.feature_tabs.data.remote.ApiService
import com.dev.miasnikoff.feature_tabs.data.remote.RemoteRepositoryImpl
import com.dev.miasnikoff.feature_tabs.data.remote.model.BookDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.BookInfoDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeResponse
import com.dev.miasnikoff.feature_tabs.domain.RemoteRepository
import com.dev.miasnikoff.feature_tabs.domain.model.OrderBy
import com.dev.miasnikoff.feature_tabs.domain.model.PrintType
import com.dev.miasnikoff.network.calladapter.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteRepositoryTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var apiService: ApiService

    lateinit var remoteRepository: RemoteRepository
    lateinit var testVolumeResponse: VolumeResponse
    lateinit var testBookDTO: BookDTO

    @Before
    fun setup() {
        remoteRepository = RemoteRepositoryImpl(apiService)
        testBookDTO = BookDTO(
            id = TEST_ID,
            selfLink = "self/link",
            bookInfo = BookInfoDTO(title = "Test Title")
        )
        testVolumeResponse =
            VolumeResponse(kind = "kind", volumes = listOf(testBookDTO), totalItems = 1)
    }


    @Test
    fun `should provide ApiResponse when Volumes list requested`() {
        val expected = ApiResponse.Success(testVolumeResponse)
        runTest {
            `when`(
                apiService.getVolumes(
                    query = TEST_QUERY,
                    filter = null,
                    printType = PrintType.BOOKS.type,
                    orderBy = OrderBy.RELEVANCE.type,
                    startIndex = TEST_START_INDEX,
                    maxResults = TEST_MAX_RESULTS
                )
            )
                .thenReturn(expected)

            val actual = remoteRepository.getVolumeList(TEST_QUERY)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should provide ApiResponse when Volume requested`() {
        val expected = ApiResponse.Success(testBookDTO)
        runTest {
            `when`(apiService.getVolume(volumeId = TEST_ID)).thenReturn(expected)

            val actual = remoteRepository.getVolume(TEST_ID)
            assertEquals(expected, actual)
        }
    }

    companion object {
        private const val TEST_QUERY = "test query"
        private const val TEST_ID = "testId"
        private const val TEST_START_INDEX = 0
        private const val TEST_MAX_RESULTS = 20
    }
}