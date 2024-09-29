package com.github.cren90.orderservice.plugins.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText {
                "Hello World"
            }
        }
    }
}
