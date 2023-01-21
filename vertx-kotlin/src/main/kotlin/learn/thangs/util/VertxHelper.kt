package learn.thangs.util

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.kotlin.coroutines.awaitResult
import kotlin.reflect.KClass

object VertxHelper {
    private var instance: Vertx? = null
    private val currentInstance: Vertx = Vertx.currentContext()?.owner() ?: initVertx()

    fun initVertx(vertxOptions: VertxOptions = VertxOptions()): Vertx {
        return instance ?: Vertx.vertx(vertxOptions).also {
            instance = it
        }
    }

    suspend fun deployVerticle(cls: KClass<*>, deploymentOptions: DeploymentOptions) =
        awaitResult<String> { currentInstance.deployVerticle(cls.qualifiedName, deploymentOptions, it) }
}