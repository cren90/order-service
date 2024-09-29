package com.github.cren90.orderservice.order.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseList(
    val orders: List<OrderResponse>
)
