// package com.backend.starter

// import org.springframework.stereotype.Component
// // import javax.annotation.PostConstruct

// @Component
// class CategoryDatabase {
//     private val categories = mutableListOf<Category>()

//     // @PostConstruct
//     // private fun init() {
//     //     categories.add(Category(0, "Aanand Shekhar Roy", null))
//     //     categories.add(Category(0, "Rashi Karanpuria", null))
//     // }

//     fun getCategories() = categories

//     fun addCategory(category: Category): Boolean {
//         categories.add(category)
//         return true
//     }

//     fun getCategoryById(id: Long): Category?{
//         var category = categories.find {
//             it.id == id
//         }

//         return category
//     }

// }
