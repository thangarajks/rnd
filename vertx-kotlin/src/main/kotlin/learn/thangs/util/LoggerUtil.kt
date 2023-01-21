package learn.thangs.util

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

fun getLogger():Logger = LogManager.getLogger(Thread.currentThread().stackTrace[2].className)