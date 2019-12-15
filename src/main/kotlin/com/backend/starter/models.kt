package com.backend.starter

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class Category(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var name: String,
    val description: String?,
    var products: List<Product> = emptyList()
) {
    companion object Factory {
        private val categories = mutableListOf<Category>()
        
        fun getCategories() = categories

        fun addCategory(category: Category): Category {
            categories.add(category)
            return category
        }

        fun getCategoryById(id: Long): Category?{
            var category = categories.find {
                it.id == id
            }

            return category
        }
    }
}

data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    val name: String,
    val price: Double,
    val description: String?,
    val categories: List<Category> = emptyList()
)
