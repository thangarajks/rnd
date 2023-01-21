package learn.thangs.rest.api.handler

import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpHeaderValues
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

abstract class APIHandler {

    fun sendEmptyResponse(
        ctx: RoutingContext,
        statusCode: HttpResponseStatus = HttpResponseStatus.OK
    ){
        ctx
            .response()
            .setStatusCode(statusCode.code())
            .putHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
            .end()
    }

    fun sendResponse(
        ctx: RoutingContext,
        statusCode: HttpResponseStatus = HttpResponseStatus.OK,
        data: JsonObject,
        message: String
    ){
        ctx
            .response()
            .setStatusCode(statusCode.code())
            .putHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
            .end(JsonObject().put("data",data).put("message",message).toString())
    }

    fun sendResponse(
        ctx: RoutingContext,
        statusCode: HttpResponseStatus = HttpResponseStatus.OK,
        data: JsonArray,
        message: String
    ){
        ctx
            .response()
            .setStatusCode(statusCode.code())
            .putHeader(HttpHeaderNames.CONTENT_TYPE,HttpHeaderValues.APPLICATION_JSON)
            .end(JsonObject().put("data",data).put("message",message).toString())
    }

}