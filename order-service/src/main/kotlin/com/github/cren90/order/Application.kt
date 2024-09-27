package com.github.cren90.order

import com.github.cren90.order.plugins.routing.configureOrderRouting
import com.github.cren90.order.plugins.routing.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureOrderRouting()
}
