package com.backend.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler 

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
        post("/api/categories").handler(CategoryController().handlerPostCategories)
        get("/api/categories").handler(CategoryController().handlerGetCategories)
        get("/api/categories/:id").handler(CategoryController().handlerGetCategory)
        put("/api/categories/:id").handler(CategoryController().handlerPutCategories)
        delete("/api/categories/:id").handler(CategoryController().handlerDeleteCategories)
        
        post("/api/products").handler(ProductController().handlerPostProducts)
        get("/api/products").handler(ProductController().handlerGetProducts)
        get("/api/products/:id").handler(ProductController().handlerGetProduct)
        put("/api/products/:id").handler(ProductController().handlerPutProducts)
        delete("/api/products/:id").handler(ProductController().handlerDeleteProducts)

        get("/api/message").handler({ rc ->
            println(123123123)
            rc.response().end("Hello React from Vert.x!")
        })

        get("/*").handler(handlerRoot)
    }
    
    val handlerRoot = StaticHandler.create().setCachingEnabled(false)
}
