package com.backend.starter

import org.springframework.beans.factory.annotation.Autowired
import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.core.http.HttpServerResponse
import com.fasterxml.jackson.module.kotlin.*


class CategoryController() {
    // var router = Router.router(vertx)
    // var apiRoutes = router.route("/api/categories")

    // private fun createRouter() = Router.router(vertx).apply {
    //     get("/").handler(handlerRoot)
    //     get("/api/categories").handler(handlerCategories)
    //     get("/api/products").handler(handlerCountries)
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

            if (catId == null) {
                req.response().setStatusCode(404).end("NOT FOUND")
            }

            // This fails with products

            val bodyJson = JsonObject(body)
            // println("1111 $bodyJson")
            val mappedCategory: Category = mapper.readValue(bodyJson.toString())
            // println("2222 $mappedCategory")

            categoriesDb.editCategoryById(catId, mappedCategory)
    
            req.response().endWithJson(mappedCategory)
        })
    }
    
    val handlerDeleteCategories = Handler<RoutingContext> { req ->
        req.request().bodyHandler({ body ->
            val bodyJson = JsonObject(body)
            val mappedCategory: Category = mapper.readValue(bodyJson.toString())

            // categoriesDb.editCategory(mappedCategory)
    
            req.response().endWithJson(mappedCategory)
        })
    }

    fun HttpServerResponse.endWithJson(obj: Any) { 
        this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
    }
}


