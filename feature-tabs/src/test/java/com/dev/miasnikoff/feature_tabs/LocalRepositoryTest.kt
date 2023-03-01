package com.dev.miasnikoff.feature_tabs

import com.dev.miasnikoff.feature_tabs.data.local.BookDao
import com.dev.miasnikoff.feature_tabs.data.local.BookEntity
import com.dev.miasnikoff.feature_tabs.data.local.LocalRepositoryImpl
import com.dev.miasnikoff.feature_tabs.domain.LocalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.times
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class LocalRepositoryTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var bookDao: BookDao

    lateinit var localRepository: LocalRepository
    lateinit var testBookEntity: BookEntity

    @Before
    fun setup() {
        localRepository = LocalRepositoryImpl(bookDao)
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
            averageRating = 5.0f,
            ratingsCount = 0,
            imageLinkSmall = null,
            imageLinkLarge = null,
            language = null,
            previewLink = null,
            infoLink = null,
            canonicalVolumeLink = null,
            inFavorite = true
        )
    }

    @Test
    fun `should return all books from database`() {
        val expected = listOf(testBookEntity)
        runTest {
            `when`(bookDao.getAllData()).thenReturn(expected)

            val actual = localRepository.getAll()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return empty list if IOException was thrown when all books were requested`() {
        val expected = listOf<BookEntity>()
        runTest {
            given(bookDao.getAllData()).willAnswer { (throw IOException()) }

            val actual = localRepository.getAll()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return all books in History from database`() {
        val expected = listOf(testBookEntity)
        runTest {
            `when`(bookDao.getAllHistory()).thenReturn(expected)

            val actual = localRepository.getAllHistory()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return empty list if IOException was thrown when all History was requested`() {
        val expected = listOf<BookEntity>()
        runTest {
            given(bookDao.getAllHistory()).willAnswer { (throw IOException()) }

            val actual = localRepository.getAllHistory()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return all books in Favorite from database`() {
        val expected = listOf(testBookEntity)
        runTest {
            `when`(bookDao.getAllFavorite()).thenReturn(expected)

            val actual = localRepository.getAllFavorite()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return empty list if IOException was thrown when all Favorite was requested`() {
        val expected = listOf<BookEntity>()
        runTest {
            given(bookDao.getAllFavorite()).willAnswer { (throw IOException()) }

            val actual = localRepository.getAllFavorite()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return true when all books in History removed`() {
        val expected = true
        runTest {
            val actual = localRepository.removeAllHistory()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return false if IOException was thrown when remove all History was requested`() {
        val expected = false
        runTest {
            given(bookDao.deleteAllHistory()).willAnswer { (throw IOException()) }

            val actual = localRepository.removeAllHistory()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return true when all books in Favorite removed`() {
        val expected = true
        runTest {
            val actual = localRepository.removeAllFavorite()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return false if IOException was thrown when remove all Favorite was requested`() {
        val expected = false
        runTest {
            given(bookDao.deleteAllFavorite()).willAnswer { (throw IOException()) }

            val actual = localRepository.removeAllFavorite()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return one book entity when getBook by id requested`() {
        val expected = testBookEntity
        runTest {
            `when`(bookDao.getBook(TEST_ID)).thenReturn(listOf(testBookEntity))

            val actual = localRepository.getBook(TEST_ID)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return null if Exception was thrown when getBook by id requested`() {
        val expected = null
        runTest {
            given(bookDao.getBook(TEST_ID)).willAnswer { throw Exception() }

            val actual = localRepository.getBook(TEST_ID)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should insert book into database when saveBook method executed`() {
        runTest {
            localRepository.saveBook(testBookEntity)
            verify(bookDao, times(1)).insertBook(testBookEntity)
        }
    }

    @Test
    fun `should delete book from database when removeBook method executed`() {
        runTest {
            localRepository.removeBook(TEST_ID)
            verify(bookDao, times(1)).deleteBook(TEST_ID)
        }
    }

    companion object {
        private const val TEST_ID = "testId"
    }
}