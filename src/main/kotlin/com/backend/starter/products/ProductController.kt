package com.backend.starter

import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import com.fasterxml.jackson.module.kotlin.*
import org.springframework.beans.factory.annotation.Autowired

class ProductController() {
    @Autowired
    private var productsDb = Product.Factory
    private val mapper = jacksonObjectMapper()

    val handlerGetProducts = Handler<RoutingContext> { req ->
        var productList = productsDb.getProducts()

        req.response().endWithJson(productList)
    }

    val handlerGetProduct = Handler<RoutingContext> { req ->
        val prodId = req.request().getParam("id").toLongOrNull()
        var product : Product? = null

        if (prodId != null) {
            product = productsDb.getProductById(prodId)
        }

        if (prodId == null || product == null) {
            req.response().setStatusCode(404).end("RECORD NOT FOUND")
        } else {
            req.response().endWithJson(product)
        }

    }

    val handlerPostProducts = Handler<RoutingContext> { req ->
        req.request().bodyHandler({ body ->
            val bodyJson = JsonObject(body)
            val mappedProduct: Product = mapper.readValue(bodyJson.toString())

            productsDb.addProduct(mappedProduct)
    
            req.response().endWithJson(mappedProduct)
        })
    }

    val handlerPutProducts = Handler<RoutingContext> { req ->
        req.request().bodyHandler({ body ->
            val prodId = req.request().getParam("id").toLongOrNull()

            if (prodId == null) {
                req.response().setStatusCode(404).end("NOT FOUND")
            }

            // This fails with Products in body

            val bodyJson = JsonObject(body)
            val mappedProduct: Product = mapper.readValue(bodyJson.toString())

            productsDb.editProduct(prodId, mappedProduct)
    
            req.response().endWithJson(mappedProduct)
        })
    }
    
    val handlerDeleteProducts = Handler<RoutingContext> { req ->
        req.request().bodyHandler({ body ->
            val prodId = req.request().getParam("id").toLongOrNull()
            val deleted = productsDb.deleteProduct(prodId)

            if (prodId == null || deleted == null) {
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
