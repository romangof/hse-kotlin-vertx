package com.backend.starter

import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import com.fasterxml.jackson.module.kotlin.*
import org.springframework.beans.factory.annotation.Autowired

class CategoryController() {
    // Can I declare de routes handlers here?

    // var router = Router.router(vertx)
    // var apiRoutes = router.route("/api/categories")

    // private fun createRouter() = Router.router(vertx).apply {
    //     get("/").handler(handlerRoot)
    //     get("/api/categories").handler(handlerCategories)
    // }

    @Autowired
    private var categoriesDb = Category.Factory
    private val mapper = jacksonObjectMapper()

    val handlerGetCategories = Handler<RoutingContext> { req ->
        fun categories() = categoriesDb.getCategories()
        println(categories())
        var list = categoriesDb.getCategories()
        println(list)

        val categoryList = categories()
        req.response().endWithJson(categoryList)
    }

    val handlerPostCategories = Handler<RoutingContext> { req ->
        req.request().bodyHandler({ body ->
            val bodyJson = JsonObject(body)
            val mappedCategory: Category = mapper.readValue(bodyJson.toString())

            categoriesDb.addCategory(mappedCategory)
    
            req.response().endWithJson(mappedCategory)
        })
    }

    val handlerPutCategories = Handler<RoutingContext> { req ->
        req.request().bodyHandler({ body ->
            val catId = req.request().getParam("id").toLongOrNull()

            // TODO: Edit only specific fields

            if (catId == null) {
                req.response().setStatusCode(404).end("NOT FOUND")
            }

            // TODO: fix this failing with products

            val bodyJson = JsonObject(body)
            val mappedCategory: Category = mapper.readValue(bodyJson.toString())

            categoriesDb.editCategoryById(catId, mappedCategory)
    
            req.response().endWithJson(mappedCategory)
        })
    }
    
    val handlerDeleteCategories = Handler<RoutingContext> { req ->
        req.request().bodyHandler({ body ->
            val catId = req.request().getParam("id").toLongOrNull()
            val deleted = categoriesDb.deleteCategory(catId)

            if (catId == null || deleted == null) {
                req.response().setStatusCode(404).end("RECORD NOT FOUND")
            } else {
                req.response().endWithJson(deleted)
            }
        })
    }

    fun HttpServerResponse.endWithJson(obj: Any) { 
        this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
    }
}


