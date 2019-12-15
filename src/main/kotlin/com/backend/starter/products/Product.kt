package com.backend.starter

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    val name: String,
    val price: Double,
    val description: String?,
    val categories: List<Category> = emptyList()
) {
    companion object Factory {
        private val products = mutableListOf<Product>()
        
        fun getProducts() = products

        fun addProduct(product: Product): Product {
            product.id = products.size.toLong()
            products.add(product)
            return product
        }

        fun getProductById(id: Long): Product?{
            var product = products.find {
                it.id == id
            }

            return product
        }
        
        fun editProductById(id: Long?, body: Product): Product?{
            var product = products.indexOfFirst {
                it.id == id
            }

            products.set(product, body)

            return products.find{ it.id == id }
        }
    }
}