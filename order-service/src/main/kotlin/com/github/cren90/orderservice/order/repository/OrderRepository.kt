package com.github.cren90.orderservice.order.repository

import com.github.cren90.orderservice.order.dto.response.OrderResponse

interface OrderRepository {
    fun saveOrder(orderResponse: OrderResponse)
    fun getOrder(id: String): OrderResponse?
    fun getAllOrders(): List<OrderResponse>
}