package com.backend.starter

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class Category(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    val description: String?,
    var products: List<Product> = emptyList()
) {
    companion object Factory {
        private val categories = mutableListOf<Category>()
        
        fun getCategories() = categories

        fun addCategory(category: Category): Category {
            // Make names unique
            category.id = categories.size.toLong()
            categories.add(category)
            return category
        }

        fun getCategoryById(id: Long): Category?{
            var category = categories.find {
                it.id == id
            }

            return category
        }

        fun deleteCategory(id: Long?): Category?{
            var category = categories.find {
                it.id == id
            }

            categories.remove(category) 
            
            return category         
        }
        
        fun editCategoryById(id: Long?, body: Category): Category?{
            var category = categories.indexOfFirst {
                it.id == id
            }

            categories.set(category, body)

            return categories.find{ it.id == id }
        }
    }
}