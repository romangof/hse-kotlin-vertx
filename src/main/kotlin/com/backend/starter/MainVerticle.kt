package com.backend.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.http.HttpServerOptions
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
        
        get("/api/products").handler(ProductController().handlerGetProducts)
        post("/api/products").handler(ProductController().handlerPostProducts)
        put("/api/products/:id").handler(ProductController().handlerPutProducts)
        delete("/api/products/:id").handler(ProductController().handlerDeleteProducts)
    }
    
    val handlerRoot = Handler<RoutingContext> { req ->
        // react goes here
        req.response().end("Welcome!")
    }
}


