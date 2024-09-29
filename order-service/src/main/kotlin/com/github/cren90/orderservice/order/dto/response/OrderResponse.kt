package com.github.cren90.orderservice.order.dto.response

import com.github.cren90.orderservice.items.dto.response.ResponseItem
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val orderId: String,
    val orderItems: List<ResponseItem>,
    val totalCents: Int
)