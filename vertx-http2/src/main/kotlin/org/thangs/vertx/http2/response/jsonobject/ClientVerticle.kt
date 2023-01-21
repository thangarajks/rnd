package org.thangs.vertx.http2.response.jsonobject

import io.vertx.core.*
import io.vertx.core.http.HttpClient
import io.vertx.core.http.HttpClientOptions
import io.vertx.core.http.HttpClientRequest
import io.vertx.core.http.HttpClientResponse
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpVersion
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.codec.BodyCodec
import io.vertx.kotlin.core.http.httpClientOptionsOf
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger


class ClientVerticle : AbstractVerticle() {

    val atomicInteger = AtomicInteger(100)
    val responseCounter = CountDownLatch(99)

    @Throws(Exception::class)
     fun start2() {
        val start = System.currentTimeMillis()
        val client =
            WebClient.create(vertx, WebClientOptions().setHttp2MaxPoolSize(10).setProtocolVersion(HttpVersion.HTTP_2))
        GlobalScope.launch {
            while (atomicInteger.decrementAndGet() > 0) {
                client.getAbs("https://rad-ef132551.qa-dnaspaces.io/health-check")
//                [80, "rad-ef132551.qa-dnaspaces.io", "/health-check"]
                    .`as`(BodyCodec.jsonObject())
                    .send { ar: AsyncResult<HttpResponse<JsonObject?>> ->
                        if (ar.succeeded()) {
                            val response: HttpResponse<JsonObject?> = ar.result()
                            println("Got HTTP response body")
                            println(response.body()?.encodePrettily())
                            responseCounter.countDown()
                            println("remaining responses : ${responseCounter.count}")
                        } else {
                            ar.cause().printStackTrace()
                        }
                    }
            }
        }
        responseCounter.await()
        println("Time taken to process 10k responses ${System.currentTimeMillis() - start} ms")

    }

    @Throws(java.lang.Exception::class)
    override fun start() {
        println("starting request")
        val start = System.currentTimeMillis()
        val options = HttpClientOptions().setProtocolVersion(HttpVersion.HTTP_2)
                .setSsl(true)
                .setUseAlpn(true)
                .setTrustAll(true)
                .setLogActivity(true)
        println("creating client")
        val client: HttpClient = vertx.createHttpClient(options)
        println("sending request")
        client.request(
            HttpMethod.GET, 443, "rad-ef132551.qa-dnaspaces.io", "/health-check"
        ) { ar1: AsyncResult<HttpClientRequest?> ->
            if (ar1.succeeded()) {
                println("Request received")
                ar1.result()?.send{ar2 : AsyncResult<HttpClientResponse?> ->
                    if (ar2.succeeded()) {
                        val response = ar2.result()
                        println("Received response with status code " + response?.statusCode())
                    }
                    else {
                        println("Received error")
                        println(ar2.cause())
                    }

                }
                // Connected to the server
            }
        }
        println("Time taken to process 10k responses ${System.currentTimeMillis() - start} ms")
    }
}

fun main() {
    val options = VertxOptions()
    val vertx = Vertx.vertx(options)
    vertx.deployVerticle(ClientVerticle::class.java, DeploymentOptions()) {

    }
}