package learn.thangs.thread

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


fun main() {
    val executor = Executors.newCachedThreadPool()
    val sum = AtomicInteger(0)
    val tasks: Array<CompletableFuture<*>> = Array(1000) { i ->
        CompletableFuture.supplyAsync(
            // create lambda expression for Supplier<Int>
            {
                TimeUnit.SECONDS.sleep(1)
                i
            },
            // executor service to execute the threads
            executor
        ).thenAccept(sum::getAndAdd)
    }
    CompletableFuture.allOf(*tasks).thenAccept {
        println("Sum : $sum")
    }
}