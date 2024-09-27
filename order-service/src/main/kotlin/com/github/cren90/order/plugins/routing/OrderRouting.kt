package com.github.cren90.order.plugins.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureOrderRouting() {
    routing {
        get("/orders") {
            call.respondText {
                "Hello Orders"
            }
        }
    }
}