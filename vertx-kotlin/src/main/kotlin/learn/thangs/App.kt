package learn.thangs

import io.vertx.core.DeploymentOptions
import kotlinx.coroutines.runBlocking
import learn.thangs.rest.RESTAPIVertical
import learn.thangs.util.VertxHelper
import learn.thangs.util.getLogger

fun main() = runBlocking{
    val logger = getLogger()
    logger.info("Starting App")

    val deploymentOptions = DeploymentOptions()
    deploymentOptions.instances = 1
    VertxHelper.deployVerticle(RESTAPIVertical::class,deploymentOptions)

    logger.info("Started App!")
}