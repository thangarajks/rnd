package learn.thangs.rest.api.controller

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import learn.thangs.rest.api.handler.JWTRequestHandler

object JWTRequestController : Controller {
    override fun getRouter(vertx: Vertx): Router {
        val router = Router.router(vertx)
        router.get("/new").handler{ctx -> JWTRequestHandler.generateNewJWT(ctx)}
        return router
    }
}