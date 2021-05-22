package com.adademy.discovery.model

data class Category(
    val name: String?
)

data class CategoryList(
    val categories: List<Category>?
)

data class CategoriesResponse(
    val data: CategoryList?
)