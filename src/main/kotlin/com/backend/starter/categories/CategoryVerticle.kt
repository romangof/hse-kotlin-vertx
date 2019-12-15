// package com.backend.starter

// import io.vertx.core.AbstractVerticle
// import io.vertx.core.Handler
// import io.vertx.core.Promise
// import io.vertx.core.json.Json
// import io.vertx.ext.web.Router
// import io.vertx.ext.web.RoutingContext
// import io.vertx.core.http.HttpServerResponse


// class CategoriesVerticle : AbstractVerticle() {
//     var router = Router.router(vertx)

//     var apiRoutes = router.route("/api/categories")

//     private fun createRouter() = Router.router(vertx).apply {
//         get("/").handler(handlerRoot)
//         get("/api/categories").handler(handlerCategories)
//         get("/api/products").handler(handlerCountries)
//     }

//     val handlerRoot = Handler<RoutingContext> { req ->
//         req.response().end("Welcome!")
//     }

//     val handlerCategories = Handler<RoutingContext> { req ->
//         req.response().endWithJson(MOCK_PRODUCTS)
//     }

//     val handlerCountries = Handler<RoutingContext> { req ->
//         // req.response().endWithJson(MOCK_PRODUCTS.map { it.country }.distinct().sortedBy { it.code })
//     }

//     private val MOCK_PRODUCTS by lazy {
//         listOf(
//             Category(0, "Legendary", null, listOf(Product(0, "Sword", 300.0, null))),
//             Category(0, "Epic", null, listOf(Product(0, "Hammer", 300.0, null))),
//             Category(0, "Rare", null, listOf(Product(0, "Gun", 300.0, null))),
//             Category(0, "Normal", null, listOf(Product(0, "Dagger", 300.0, null)))
//         )
//     }

//     fun HttpServerResponse.endWithJson(obj: Any) { 
//         // This handler will be called for every request
//         // var response = routingContext.response()
//         // response.putHeader("content-type", "text/plain")

//         // // Write to the response and end it
//         // response.end("Hello World from Vert.x-Web!")

//         this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
//     }
//     // apiRoutes.handler({ routingContext ->
//     //     routingContext.response().end("Categories \n from Vert.x!")
//     //     routingContext.next()
//     // })
// }


