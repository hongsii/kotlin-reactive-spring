package dev.hongsii.common.extensions

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

inline fun <reified R : Any> R.logger(): Logger = LogManager.getLogger(this::class.java.name.substringBefore("\$Companion"))
