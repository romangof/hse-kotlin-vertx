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

        get("/api/categories").handler(CategoryController().handlerGetCategories)
        post("/api/categories").handler(CategoryController().handlerPostCategories)
        put("/api/categories/:id").handler(CategoryController().handlerPutCategories)
        delete("/api/categories/:id").handler(CategoryController().handlerDeleteCategories)
        
        // get("/api/products").handler(handlerGetProducts)
        // post("/api/products").handler(handlerPostProducts)
        // put("/api/products/:id").handler(handlerPutProducts)
        // delete("/api/products/:id").handler(handlerDeleteProducts)
    }
    
    // Handlers
    @Autowired
    private var categoriesDb = Category.Factory
    private val mapper = jacksonObjectMapper()

    val handlerRoot = Handler<RoutingContext> { req ->
        // react goes here
        req.response().end("Welcome!")
    }

    val handlerCountries = Handler<RoutingContext> { req ->
        println(req)
        // req.response().endWithJson(MOCK_PRODUCTS.map { it.country }.distinct().sortedBy { it.code })
    }

    /**
     * Extension to the HTTP response to output JSON objects.
     */
    fun HttpServerResponse.endWithJson(obj: Any) { 
        this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
    }
}


