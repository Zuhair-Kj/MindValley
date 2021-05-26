package com.adademy.discovery

import com.adademy.discovery.cache.CategoriesDao
import com.adademy.discovery.model.*
import com.adademy.discovery.network.DiscoveryService
import com.adademy.discovery.repository.CategoriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoriesRepositoryTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var categoriesRepository: CategoriesRepository

    @Mock
    lateinit var discoveryService: DiscoveryService

    @Mock
    lateinit var categoriesDao: CategoriesDao

    @Before
    fun init() {
        categoriesRepository = CategoriesRepository(discoveryService, categoriesDao)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return results after saving to DB`() {
        val mockItems = listOf<Category>(Mockito.mock(Category::class.java))
        testDispatcher.runBlockingTest {
            Mockito.`when`(discoveryService.getCategories()).thenReturn(CategoriesResponse(CategoryList(mockItems)))
            val categories = categoriesRepository.getCategories()
            Mockito.verify(discoveryService).getCategories()
            Mockito.verify(categoriesDao).insertRows(mockItems)
            Assert.assertEquals(categories, mockItems)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return cached result`() {
        val mockItems = listOf<Category>(Mockito.mock(Category::class.java))
        testDispatcher.runBlockingTest {
            Mockito.`when`(categoriesDao.getAllRows()).thenReturn(mockItems)
            val categories = categoriesRepository.getCachedCategories()
            Mockito.verifyNoInteractions(discoveryService)
            Mockito.verify(categoriesDao).getAllRows()
            Assert.assertEquals(categories, mockItems)
        }
    }
}