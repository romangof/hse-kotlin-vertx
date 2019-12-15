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

class ProductController() {
    @Autowired
    private var productsDb = Product.Factory
    private val mapper = jacksonObjectMapper()

    val handlerGetProducts = Handler<RoutingContext> { req ->
        fun products() = productsDb.getProducts()
        println(products())
        var list = productsDb.getProducts()
        println(list)

        val productList = products()
        req.response().endWithJson(productList)
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

            productsDb.editProductById(prodId, mappedProduct)
    
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
