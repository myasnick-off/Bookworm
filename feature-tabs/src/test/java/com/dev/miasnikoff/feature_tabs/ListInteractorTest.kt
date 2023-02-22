package com.dev.miasnikoff.feature_tabs

import com.dev.miasnikoff.feature_tabs.data.local.BookEntity
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeInfoDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeResponse
import com.dev.miasnikoff.feature_tabs.domain.LocalRepository
import com.dev.miasnikoff.feature_tabs.domain.RemoteRepository
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractor
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractorImpl
import com.dev.miasnikoff.feature_tabs.domain.mapper.BookEntityDataMapper
import com.dev.miasnikoff.feature_tabs.domain.model.Either
import com.dev.miasnikoff.feature_tabs.domain.model.Filter
import com.dev.miasnikoff.feature_tabs.domain.model.OrderBy
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import com.dev.miasnikoff.network.calladapter.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@OptIn(ExperimentalCoroutinesApi::class)
class ListInteractorTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var remoteRepository: RemoteRepository

    @Mock
    lateinit var localRepository: LocalRepository

    lateinit var listInteractor: ListInteractor
    lateinit var mapper: BookEntityDataMapper
    lateinit var testVolumeResponse: VolumeResponse
    lateinit var testBookEntity: BookEntity
    lateinit var testBookItem: BookItem

    @Before
    fun setup() {
        mapper = BookEntityDataMapper()
        listInteractor = ListInteractorImpl(remoteRepository, localRepository, mapper)
        testVolumeResponse = VolumeResponse(
            kind = "kind",
            volumes = listOf(
                VolumeDTO(
                    id = TEST_ID,
                    selfLink = "self/link",
                    volumeInfo = VolumeInfoDTO(title = "Test Title")
                )
            ),
            totalItems = 1
        )
        testBookEntity = BookEntity(
            id = TEST_ID,
            title = "Test Title",
            subtitle = null,
            authors = null,
            publisher = null,
            publishedDate = null,
            description = null,
            pageCount = null,
            printType = null,
            mainCategory = null,
            categories = null,
            averageRating = null,
            ratingsCount = null,
            imageLinkSmall = null,
            imageLinkLarge = null,
            language = null,
            previewLink = null,
            infoLink = null,
            canonicalVolumeLink = null,
            inFavorite = true
        )
        testBookItem = BookItem(
            id = TEST_ID,
            title = "Test Title",
            authors = "",
            publishedDate = "",
            mainCategory = "",
            averageRating = 5.0f,
            imageLink = null,
            language = ""
        )
    }

    @Test
    fun `should emit Success result when remote repository returns success response`() {
        val expected = Either.Success(data = testVolumeResponse)
        runTest {
            `when`(
                remoteRepository.getVolumeList(
                    query = TEST_QUERY,
                    filter = Filter.FREE_BOOKS.type,
                    orderBy = OrderBy.NEWEST.type,
                    startIndex = TEST_START_INDEX,
                    maxResults = TEST_MAX_RESULTS
                )
            )
                .thenReturn(ApiResponse.Success(testVolumeResponse))

            `when`(localRepository.getAllFavorite()).thenReturn(listOf())

            val actual = listInteractor.getBooksList(
                query = TEST_QUERY,
                filter = Filter.FREE_BOOKS.type,
                orderBy = OrderBy.NEWEST.type,
                startIndex = TEST_START_INDEX,
                maxResults = TEST_MAX_RESULTS
            )
            assertNotNull(actual)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should emit Error result when remote repository returns Failure responses`() {
        val expected = Either.Error(message = TEST_ERROR_MESSAGE)
        runTest {
            `when`(
                remoteRepository.getVolumeList(
                    query = TEST_QUERY,
                    filter = Filter.FREE_BOOKS.type,
                    orderBy = OrderBy.NEWEST.type,
                    startIndex = TEST_START_INDEX,
                    maxResults = TEST_MAX_RESULTS
                )
            )
                .thenReturn(ApiResponse.Failure.UnknownFailure(Exception(TEST_ERROR_MESSAGE)))

            `when`(localRepository.getAllFavorite()).thenReturn(listOf())

            val actual = listInteractor.getBooksList(
                query = TEST_QUERY,
                filter = Filter.FREE_BOOKS.type,
                orderBy = OrderBy.NEWEST.type,
                startIndex = TEST_START_INDEX,
                maxResults = TEST_MAX_RESULTS
            )
            assertNotNull(actual)
            assertEquals(expected, actual)
            assertSame(expected.message, (actual as Either.Error).message)
        }
    }

    @Test
    fun `should mark remote books as favorite if they saved in local database`() {
        val expectedVolumes = listOf(
            VolumeDTO(
                id = TEST_ID,
                selfLink = "self/link",
                volumeInfo = VolumeInfoDTO(title = "Test Title"),
                isFavorite = true
            )
        )
        val expected = Either.Success(data = testVolumeResponse.copy(volumes = expectedVolumes))
        runTest {
            `when`(
                remoteRepository.getVolumeList(
                    query = TEST_QUERY,
                    filter = Filter.FREE_BOOKS.type,
                    orderBy = OrderBy.NEWEST.type,
                    startIndex = TEST_START_INDEX,
                    maxResults = TEST_MAX_RESULTS
                )
            )
                .thenReturn(ApiResponse.Success(testVolumeResponse))

            `when`(localRepository.getAllFavorite()).thenReturn(listOf(testBookEntity))

            val actual = listInteractor.getBooksList(
                query = TEST_QUERY,
                filter = Filter.FREE_BOOKS.type,
                orderBy = OrderBy.NEWEST.type,
                startIndex = TEST_START_INDEX,
                maxResults = TEST_MAX_RESULTS
            )
            assertNotNull(actual)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should execute local repository save method when save book in favorite`() {
        runTest {
            `when`(localRepository.getAllHistory()).thenReturn(listOf())
            val favoriteEntity = mapper.toFavorite(testBookItem)

            listInteractor.saveInFavorite(testBookItem)
            verify(localRepository, times(1))
                .saveBook(favoriteEntity)
        }
    }

    @Test
    fun `should remove favorite book from db if it is not either in history`() {
        runTest {
            `when`(localRepository.getAllHistory()).thenReturn(listOf())

            listInteractor.removeFromFavorite(testBookItem)
            verify(localRepository, times(1))
                .removeBook(testBookItem.id)
        }
    }

    @Test
    fun `should uncheck book as favorite in db if it is either in history`() {
        runTest {
            `when`(localRepository.getAllHistory()).thenReturn(listOf(testBookEntity))

            listInteractor.removeFromFavorite(testBookItem)
            verify(localRepository, times(1))
                .saveBook(testBookEntity.copy(inFavorite = false))
        }
    }

    @Test
    fun `should remove inHistory book from db if it is not either in favorite`() {
        runTest {
            listInteractor.removeFromHistory(testBookItem)
            verify(localRepository, times(1))
                .removeBook(testBookItem.id)
        }
    }

    @Test
    fun `should uncheck book as inHistory in db if it is either in favorite`() {
        runTest {
            listInteractor.removeFromHistory(testBookItem.copy(isFavorite = true))
            val actualEntity = mapper.toFavorite(testBookItem).copy(inHistory = false)
            verify(localRepository, times(1))
                .saveBook(actualEntity)
        }
    }

    @Test
    fun `should return all history list from database`() {
        runTest {
            `when`(localRepository.getAllHistory()).thenReturn(listOf(testBookEntity))
            val actual = listInteractor.getHistory()

            verify(localRepository, times(1))
                .getAllHistory()
            assertNotNull(actual)
            assertEquals(listOf(testBookEntity), actual)
        }
    }

    @Test
    fun `should return all favorite list from database`() {
        runTest {
            `when`(localRepository.getAllFavorite()).thenReturn(listOf(testBookEntity))
            val actual = listInteractor.getFavorite()

            verify(localRepository, times(1))
                .getAllFavorite()
            assertNotNull(actual)
            assertEquals(listOf(testBookEntity), actual)
        }
    }

    @Test
    fun `should remove all history from database`() {
        runTest {
            listInteractor.removeAllHistory()

            verify(localRepository, times(1))
                .removeAllHistory()
        }
    }

    @Test
    fun `should remove all favorite from database`() {
        runTest {
            listInteractor.removeAllFavorite()

            verify(localRepository, times(1))
                .removeAllFavorite()
        }
    }

    companion object {
        private const val TEST_QUERY = "test query"
        private const val TEST_ID = "testId"
        private const val TEST_START_INDEX = 0
        private const val TEST_MAX_RESULTS = 20
        private const val TEST_ERROR_MESSAGE = "Test error message"
    }
}