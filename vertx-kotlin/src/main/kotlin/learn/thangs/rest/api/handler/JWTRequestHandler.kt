package learn.thangs.rest.api.handler

import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.JWTOptions
import io.vertx.ext.web.RoutingContext
import learn.thangs.AppSecret
import learn.thangs.rest.api.auth.AuthHelper


object JWTRequestHandler : APIHandler() {
    fun generateNewJWT(ctx: RoutingContext) {
        val token: String =
            AuthHelper.getJWTAuthProvider(Vertx.vertx(), AppSecret.JWT_PUBLIC_KEY, AppSecret.JWT_PRIVATE_KEY)
                .generateToken(
                    JsonObject().put("some", "token-data"), JWTOptions().setAlgorithm("RS256")
                )
        sendResponse(
            ctx = ctx,
            statusCode = HttpResponseStatus.OK,
            message = "Generated New JWT token",
            data = JsonObject().put("token",token)
        )
    }
}