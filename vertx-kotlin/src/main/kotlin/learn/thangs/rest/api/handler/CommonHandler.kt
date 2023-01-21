package learn.thangs.rest.api.handler

import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.HttpException
import learn.thangs.util.getLogger


object CommonHandler : APIHandler() {
    private val logger = getLogger()

    fun healthCheckHandler(ctx: RoutingContext) {
        logger.debug("healthCheckHandler()")
        sendResponse(
            ctx = ctx,
            statusCode = HttpResponseStatus.OK,
            message = "API is Healthy",
            data = JsonObject().put("status", "ok")
        )
    }

    fun failureHandler(ctx: RoutingContext) {
        logger.error(ctx.failure())
        when (ctx.failure()) {
            is HttpException -> handleHttpException(ctx)
            else -> handleInternalServerError(ctx)
        }
    }

    private fun handleHttpException(ctx: RoutingContext) {
        when (ctx.failure().message) {
            "Unauthorized" -> {
                sendResponse(
                    ctx = ctx,
                    statusCode = HttpResponseStatus.UNAUTHORIZED,
                    message = ctx.failure().message ?: "",
                    data = JsonObject().put("error", ctx.failure().message)
                )
            }
            else -> handleInternalServerError(ctx)
        }
    }

    private fun handleInternalServerError(ctx: RoutingContext) {
        sendResponse(
            ctx = ctx,
            statusCode = HttpResponseStatus.INTERNAL_SERVER_ERROR,
            message = ctx.failure().message ?: "",
            data = JsonObject().put("error", ctx.failure().message)
        )
    }

}