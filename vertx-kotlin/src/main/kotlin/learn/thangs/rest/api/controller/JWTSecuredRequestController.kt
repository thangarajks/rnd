package learn.thangs.rest.api.controller

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import learn.thangs.rest.api.handler.JWTSecuredRequestHandler

object JWTSecuredRequestController : Controller {
    override fun getRouter(vertx: Vertx): Router {
        val router = Router.router(vertx)
        router.get("/user/detail").handler { ctx -> JWTSecuredRequestHandler.currentUserDetails(ctx) }
        return router
    }
}