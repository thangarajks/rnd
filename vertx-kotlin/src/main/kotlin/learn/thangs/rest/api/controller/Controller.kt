package learn.thangs.rest.api.controller

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

/**
 *
 * controller interface defining base methods a controller should implement.
 * By Implementing this interface enables a class to act as a controller which can handler requests to assigned paths
 * Controller provides a router which is responsible for routing requests to defined respective handlers
 *
 *
 * @author thangarajks
 * @version 1.0.0
 * @since 1.0.0
 *
 */
interface Controller {
    /**
     * Method that returns an io.vertx.ext.web.Router instance which can be mounted to a path to handle requests to that path
     *
     * @param vertx Vertx instance for which router object has to be created
     *
     * @return Router instance that has handlers registered against request paths.
     */
    fun getRouter(vertx: Vertx): Router
}
