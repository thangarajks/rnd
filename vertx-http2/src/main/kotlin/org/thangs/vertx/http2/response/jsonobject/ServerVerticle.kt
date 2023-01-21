package org.thangs.vertx.http2.response.jsonobject

import io.vertx.core.*
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerRequest
import io.vertx.core.json.JsonObject


class ServerVerticle : AbstractVerticle() {

    @Throws(Exception::class)
    override fun start() {
        vertx.createHttpServer().requestHandler { req: HttpServerRequest ->
            vertx.setTimer(1000) {
            req.response()
//                .putHeader("Content/type", "application/json")
                .end(
                    JsonObject()
                        .put("firstName", "Dale")
                        .put("lastName", "Cooper")
                        .put("male", true)
                        .encode()
                )
        }
        }.listen(8080) { listenResult: AsyncResult<HttpServer?> ->
            if (listenResult.failed()) {
                println("Could not start HTTP server")
                listenResult.cause().printStackTrace()
            } else {
                println("Server started")
            }
        }
    }
}

fun main(){

    val options = VertxOptions()
    val vertx = Vertx.vertx(options)
    vertx.deployVerticle(ServerVerticle::class.java, DeploymentOptions()){

    }
}