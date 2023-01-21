package learn.thangs.rest

import io.vertx.core.AsyncResult
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.JWTAuthHandler
import io.vertx.ext.web.handler.LoggerHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import learn.thangs.AppConfig
import learn.thangs.AppSecret
import learn.thangs.rest.api.auth.AuthHelper
import learn.thangs.rest.api.controller.HealthCheckController
import learn.thangs.rest.api.controller.JWTRequestController
import learn.thangs.rest.api.controller.JWTSecuredRequestController
import learn.thangs.rest.api.handler.CommonHandler
import learn.thangs.util.getLogger


class RESTAPIVertical : CoroutineVerticle() {

    // get logger object to capture and handle logs
    private val logger = getLogger()

    override suspend fun start() {
        val router = Router.router(vertx)

        // LoggerHandler to log all served requests
        router.route("/*").handler(LoggerHandler.create())

        // Parse body and file contents from request to RoutingContext
        router.route("/*").handler(BodyHandler.create())

        // Secure all requests to this path with JWT token.
        router.route("/jwt/secured/*").handler(
            JWTAuthHandler.create(
                // get a JWT Auth provider using given public key
                AuthHelper.getJWTAuthProvider(vertx, AppSecret.JWT_PUBLIC_KEY, AppSecret.JWT_PRIVATE_KEY)
            )
        )

        // HealthCheck controller attached to path root
        router.route("/*").subRouter(HealthCheckController.getRouter(vertx))

        // Controller to handle requests to jwt secured paths
        router.route("/jwt/secured/*").subRouter(JWTSecuredRequestController.getRouter(vertx))

        // Controller to handle requests to jwt paths
        router.route("/jwt/*").subRouter(JWTRequestController.getRouter(vertx))

        // Failure handler to catch any unhandled exception and send a failure response
        router.route("/*").failureHandler { ctx -> CommonHandler.failureHandler(ctx) }

        vertx
            // create a http server from vertx
            .createHttpServer()
            // assign the  router to handle all the requests comming in
            .requestHandler { router.handle(it) }
            // start listening to the  HTTP requests on given port
            .listen(AppConfig.HTTP_SERVER_PORT) { listnerResult: AsyncResult<HttpServer?> ->
                if (listnerResult.failed()) {
                    logger.error(
                        "Failed to Start HTTP Server on port : ${AppConfig.HTTP_SERVER_PORT}", listnerResult.cause()
                    )
                } else {
                    logger.info("Successfully Started HTTP Server on port : ${AppConfig.HTTP_SERVER_PORT}")
                }
            }


    }

    override suspend fun stop() {
        super.stop()
    }

}