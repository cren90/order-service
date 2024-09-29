package com.github.cren90.orderservice.plugins.routing

import com.github.cren90.orderservice.order.domain.OrderProcessor
import com.github.cren90.orderservice.order.dto.request.OrderRequest
import com.github.cren90.orderservice.sendResultResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureOrderRouting() {
    routing {
        get("/order") {
            call.respondText {
                "Hello Orders"
            }
        }
        post("/order") {
            val orderRequest = call.receive<OrderRequest>()

            val orderResult = OrderProcessor().processOrder(orderRequest.items)

            call.sendResultResponse(orderResult, HttpStatusCode.OK)
        }
    }
}