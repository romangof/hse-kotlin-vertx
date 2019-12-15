package com.backend.starter

import org.springframework.beans.factory.annotation.Autowired
import com.fasterxml.jackson.module.kotlin.*
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext


class MainVerticle : AbstractVerticle() {
    override fun start(startFuture: Future<Void>) {
        var options = HttpServerOptions()
        val server = vertx.createHttpServer(options)
        val router = createRouter()

        server.requestHandler(router).listen(8080) { result ->
            if (result.succeeded()) {
                startFuture.complete()
            } else {
                startFuture.fail(result.cause())
            }
        }
    }

    private fun createRouter() = Router.router(vertx).apply {
        get("/").handler(handlerRoot)

        get("/api/categories").handler(handlerGetCategories)
        post("/api/categories").handler(handlerPostCategories)

        get("/api/products").handler(handlerCountries)
    }
    
    // Handlers
    @Autowired
    private var categoriesDb = Category.Factory
    private val mapper = jacksonObjectMapper()
    // private var parser = JsonParser.newParser()
    // private var totalBuffer = Buffer.buffer()
    // private fun <T> convertObject(objectClass:Class<T>, transformedObject:JsonObject):T {
    //     return transformedObject.mapTo(objectClass)
    // }

    val handlerRoot = Handler<RoutingContext> { req ->
        req.response().end("Welcome!")
    }

    val handlerGetCategories = Handler<RoutingContext> { req ->
        fun categories() = categoriesDb.getCategories()
        println(categories())
        var list = categoriesDb.getCategories()
        println(list)

        val categoryList = categories()
        req.response().endWithJson(categoryList)
    }

    val handlerPostCategories = Handler<RoutingContext> { req ->
        // req.response().endWithJson(MOCK_PRODUCTS)

        req.response().endHandler({ totalBuffer ->
            println(1111)
            // println("Full body received, length = ${totalBuffer}")
            // println("Full body received, length = ${req.getBodyAsJson()}")
        })


        req.request().bodyHandler({ body ->
            val bodyJson = JsonObject(body)
            val mappedCategory: Category = mapper.readValue(bodyJson.toString())

            categoriesDb.addCategory(mappedCategory)
    
            req.response().endWithJson(mappedCategory)
        })
    }

    val handlerCountries = Handler<RoutingContext> { req ->
        // req.response().endWithJson(MOCK_PRODUCTS.map { it.country }.distinct().sortedBy { it.code })
    }

    // private val MOCK_PRODUCTS by lazy {
    //     listOf(
    //         Category(0, "Legendary", null, listOf(Product(0, "Sword", 300.0, null))),
    //         Category(0, "Epic", null, listOf(Product(0, "Hammer", 300.0, null))),
    //         Category(0, "Rare", null, listOf(Product(0, "Gun", 300.0, null))),
    //         Category(0, "Normal", null, listOf(Product(0, "Dagger", 300.0, null)))
    //     )
    // }


    /**
     * Extension to the HTTP response to output JSON objects.
     */
    fun HttpServerResponse.endWithJson(obj: Any) { 
        this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
    }
}


