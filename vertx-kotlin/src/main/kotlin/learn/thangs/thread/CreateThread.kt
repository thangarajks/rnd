package learn.thangs.thread

class ThreadUsingThreadClass : Thread() {

    override fun run(){
        println("Running ThreadUsingThreadClass thread")

    }
}

class ThreadUsingRunnable : Runnable{
    override fun run() {
        println("Running ThreadUsingRunnable thread")
    }

}

fun main(){
    // start a thread of ThreadUsingThreadClass
    ThreadUsingThreadClass().start()
    // start a thread of ThreadUsingRunnable task
    Thread(ThreadUsingRunnable()).start()
    // Inline thread creation
    Thread { println("Running Inline thread of Thread class") }.start()
}