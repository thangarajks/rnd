package learn.thangs.rest.api.handler

import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import learn.thangs.util.getLogger

object JWTSecuredRequestHandler : APIHandler() {
    private val logger = getLogger()

    fun currentUserDetails(ctx: RoutingContext) {
        logger.trace("JWTSecuredRequestHandler.currentUserDetails()")
        val user = ctx.user()?.principal() ?: JsonObject()
        logger.debug("sending current user details")
        sendResponse(
            ctx = ctx,
            statusCode = HttpResponseStatus.OK,
            message = "Current User Details",
            data = user
        )
    }
}