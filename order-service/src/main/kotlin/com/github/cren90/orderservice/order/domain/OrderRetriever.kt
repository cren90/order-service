package com.github.cren90.orderservice.order.domain

import com.github.cren90.orderservice.ERROR_INVALID_ORDER_ID
import com.github.cren90.orderservice.ERROR_ORDER_NOT_FOUND
import com.github.cren90.orderservice.Result
import com.github.cren90.orderservice.order.dto.response.OrderResponse
import com.github.cren90.orderservice.order.dto.response.OrderResponseList
import com.github.cren90.orderservice.order.repository.OrderRepository

class OrderRetriever(private val orderRepo: OrderRepository) {
    fun getAllOrders(): Result<OrderResponseList> {
        return Result.Success(OrderResponseList(orderRepo.getAllOrders()))
    }

    fun getOrderById(orderId: String?): Result<OrderResponse> {
        if (orderId == null) {
            return Result.RequestError(ERROR_INVALID_ORDER_ID)
        }

        val order = orderRepo.getOrder(orderId)

        return if (order == null) {
            Result.NotFoundError(ERROR_ORDER_NOT_FOUND)
        } else {
            Result.Success(order)
        }
    }
}