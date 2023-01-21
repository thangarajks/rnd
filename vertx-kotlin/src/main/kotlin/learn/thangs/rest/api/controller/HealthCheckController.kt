package learn.thangs.rest.api.controller

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import learn.thangs.rest.api.handler.CommonHandler
import learn.thangs.util.getLogger

object HealthCheckController : Controller {

    private val logger = getLogger()
    override fun getRouter(vertx: Vertx): Router {
        logger.trace("HealthCheckController.getRouter()")
        val router = Router.router(vertx)
        router.get("/healthcheck").handler { ctx -> CommonHandler.healthCheckHandler(ctx) }
        return router
    }

}