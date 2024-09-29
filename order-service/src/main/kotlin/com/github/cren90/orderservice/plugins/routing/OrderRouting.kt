package com.github.cren90.orderservice.plugins.routing

import com.github.cren90.orderservice.offer.repository.InMemoryOfferRepository
import com.github.cren90.orderservice.offer.repository.OfferRepository
import com.github.cren90.orderservice.order.domain.OrderProcessor
import com.github.cren90.orderservice.order.domain.OrderRetriever
import com.github.cren90.orderservice.order.dto.request.OrderRequest
import com.github.cren90.orderservice.order.repository.InMemoryOrderRepository
import com.github.cren90.orderservice.order.repository.OrderRepository
import com.github.cren90.orderservice.sendResultResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

val offerRepository: OfferRepository = InMemoryOfferRepository
val orderRepository: OrderRepository = InMemoryOrderRepository
val orderProcessor = OrderProcessor(offerRepository, orderRepository)
val orderRetriever = OrderRetriever(orderRepository)

fun Application.configureOrderRouting() {
    routing {
        get("/orders") {
            val orderResults = orderRetriever.getAllOrders()

            call.sendResultResponse(orderResults, HttpStatusCode.OK)
        }

        get("/orders/{id}") {
            val id = call.parameters["id"]

            val orderResult = orderRetriever.getOrderById(id)

            call.sendResultResponse(orderResult, HttpStatusCode.OK)
        }

        post("/orders") {
            val orderRequest = call.receive<OrderRequest>()

            val orderResult = orderProcessor.processOrder(orderRequest.items)

            call.sendResultResponse(orderResult, HttpStatusCode.Created)
        }
    }
}