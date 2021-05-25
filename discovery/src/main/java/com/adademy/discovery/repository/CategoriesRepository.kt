package com.adademy.discovery.repository

import com.adademy.discovery.cache.CategoriesDao
import com.adademy.discovery.model.Category
import com.adademy.discovery.network.DiscoveryService

class CategoriesRepository(
    private val discoveryService: DiscoveryService,
    private val categoriesDao: CategoriesDao
) {
    suspend fun getCategories(): List<Category> {
        discoveryService.getCategories()?.data?.categories?.let {
            save(it)
            return it
        }
        return emptyList()
    }

    private suspend fun save(categories: List<Category>?) {
        categories?.let {
            categoriesDao.insertRows(it)
        }
    }

    suspend fun getCachedCategories(): List<Category> {
        return categoriesDao.getAllRows()
    }
}