package com.dev.miasnikoff.feature_tabs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeInfoDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeResponse
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractor
import com.dev.miasnikoff.feature_tabs.domain.model.Either
import com.dev.miasnikoff.feature_tabs.domain.model.Filter
import com.dev.miasnikoff.feature_tabs.domain.model.OrderBy
import com.dev.miasnikoff.feature_tabs.domain.model.QueryFields
import com.dev.miasnikoff.feature_tabs.mock.MockDirections
import com.dev.miasnikoff.feature_tabs.rule.MainDispatcherRule
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.feature_tabs.ui.list.BookListViewModel
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import com.dev.miasnikoff.feature_tabs.ui.list.mapper.DtoToUiMapper
import com.dev.miasnikoff.feature_tabs.ui.list.model.PagedListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@OptIn(ExperimentalCoroutinesApi::class)
class BookListViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var listInteractor: ListInteractor

    @Mock
    lateinit var router: FlowRouter

    lateinit var bookListViewModel: BookListViewModel
    lateinit var observer: Observer<PagedListState>
    private lateinit var mapper: DtoToUiMapper
    private lateinit var testVolumeResponse: VolumeResponse
    private lateinit var testBookItem: BookItem
    private lateinit var testBookItemFavorite: BookItem

    @Before
    fun setup() {
        mapper = DtoToUiMapper()
        bookListViewModel = BookListViewModel(listInteractor, mapper, router)
        testVolumeResponse = VolumeResponse(
            kind = "kind",
            volumes = listOf(
                VolumeDTO(
                    id = TEST_ID,
                    selfLink = "self/link1",
                    volumeInfo = VolumeInfoDTO(title = "Test Title 1")
                ),
                VolumeDTO(
                    id = TEST_ID_FAVORITE,
                    selfLink = "self/link2",
                    volumeInfo = VolumeInfoDTO(title = "Test Title 2"),
                    isFavorite = true
                )
            ),
            totalItems = 1
        )
        testBookItem = BookItem(
            id = TEST_ID,
            title = "Test Title 1",
            authors = "",
            publishedDate = "",
            mainCategory = "",
            averageRating = 0.0f,
            imageLink = null,
            language = ""
        )
        testBookItemFavorite = BookItem(
            id = TEST_ID_FAVORITE,
            title = "Test Title 2",
            authors = "",
            publishedDate = "",
            mainCategory = "",
            averageRating = 0.0f,
            imageLink = null,
            language = "",
            isFavorite = true,
            favoriteIcon = 2131230831
        )

        observer = Observer<PagedListState> {}
        bookListViewModel.liveData.observeForever(observer)
    }

    @After
    fun clear() {
        bookListViewModel.liveData.removeObserver(observer)
    }

    @Test
    fun `should execute interactor's getBooksList method when request data by query`() {
        runTest {
            bookListViewModel.getInitialPage(TEST_QUERY)
            verify(listInteractor, times(1)).getBooksList(
                query = TEST_QUERY,
                filter = null,
                orderBy = null,
                startIndex = TEST_START_INDEX,
                maxResults = TEST_MAX_RESULTS
            )
        }
    }

    @Test
    fun `should execute interactor's getBooksList method when request data by NEWEST category`() {
        runTest {
            bookListViewModel.getInitialPage(Category.NEWEST)
            verify(listInteractor, times(1)).getBooksList(
                query = QueryFields.IN_TITLE.type,
                filter = null,
                orderBy = OrderBy.NEWEST.type,
                startIndex = TEST_START_INDEX,
                maxResults = TEST_MAX_RESULTS
            )
        }
    }

    @Test
    fun `should execute interactor's getBooksList method when request data by FREE category`() {
        runTest {
            bookListViewModel.getInitialPage(Category.FREE)
            verify(listInteractor, times(1)).getBooksList(
                query = QueryFields.IN_TITLE.type,
                filter = Filter.FREE_BOOKS.type,
                orderBy = OrderBy.NEWEST.type,
                startIndex = TEST_START_INDEX,
                maxResults = TEST_MAX_RESULTS
            )
        }
    }

    @Test
    fun `should return data when initial page request success`() {
        runTest {
            `when`(
                listInteractor.getBooksList(
                    query = TEST_QUERY,
                    filter = null,
                    orderBy = null,
                    startIndex = TEST_START_INDEX,
                    maxResults = TEST_MAX_RESULTS
                )
            ).thenReturn(Either.Success(testVolumeResponse))

                bookListViewModel.getInitialPage(TEST_QUERY)
                val expectedVal = PagedListState.Success(data = listOf(testBookItem, testBookItemFavorite), loadMore = false)
                val actualVal = bookListViewModel.liveData.value as? PagedListState.Success

                assertNotNull(bookListViewModel.liveData.value)
                assertEquals(expectedVal, actualVal)
        }
    }

    @Test
    fun `should return failure state when initial page request failed`() {
        runTest {
            `when`(
                listInteractor.getBooksList(
                    query = TEST_QUERY,
                    filter = null,
                    orderBy = null,
                    startIndex = TEST_START_INDEX,
                    maxResults = TEST_MAX_RESULTS
                )
            ).thenReturn(Either.Error(TEST_ERROR_MESSAGE))

                bookListViewModel.getInitialPage(TEST_QUERY)
                val expectedVal = PagedListState.Failure(TEST_ERROR_MESSAGE)
                val actualVal = bookListViewModel.liveData.value as? PagedListState.Failure

                assertNotNull(actualVal)
                assertEquals(expectedVal, actualVal)
                assertSame(expectedVal.message, actualVal?.message)
        }
    }

    @Test
    fun `should save book in database when it added to favorite`() {
        runTest {
            `when`(
                listInteractor.getBooksList(
                    query = TEST_QUERY,
                    filter = null,
                    orderBy = null,
                    startIndex = TEST_START_INDEX,
                    maxResults = TEST_MAX_RESULTS
                )
            ).thenReturn(Either.Success(testVolumeResponse))

            bookListViewModel.getInitialPage(TEST_QUERY)
            bookListViewModel.setFavorite(TEST_ID)
            verify(listInteractor, times(1)).saveInFavorite(testBookItem)
        }
    }

    @Test
    fun `should remove book from favorite in database when uncheck as favorite`() {
        runTest {
            `when`(
                listInteractor.getBooksList(
                    query = TEST_QUERY,
                    filter = null,
                    orderBy = null,
                    startIndex = TEST_START_INDEX,
                    maxResults = TEST_MAX_RESULTS
                )
            ).thenReturn(Either.Success(testVolumeResponse))

            bookListViewModel.getInitialPage(TEST_QUERY)
            bookListViewModel.setFavorite(TEST_ID_FAVORITE)
            verify(listInteractor, times(1)).removeFromFavorite(testBookItemFavorite)
        }
    }

    @Test
    fun `should router navigate to provided direction when navigate method is executed`() {
        val direction = MockDirections()
        bookListViewModel.navigate(direction)
        verify(router, times(1)).navigateTo(direction)
    }

    @Test
    fun `should router navigate back when back method is executed`() {
        bookListViewModel.back()
        verify(router, times(1)).back()
    }

    companion object {
        private const val TEST_QUERY = "test query"
        private const val TEST_ID = "testId"
        private const val TEST_ID_FAVORITE = "testId_favorite"
        private const val TEST_START_INDEX = 0
        private const val TEST_MAX_RESULTS = 20
        private const val TEST_ERROR_MESSAGE = "Test error message"
    }
}