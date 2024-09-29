package com.github.cren90.orderservice.items.dto.response

import com.github.cren90.orderservice.items.Item
import kotlinx.serialization.Serializable

@Serializable
data class ResponseItem(
    val item: Item,
    val quantity: Int,
    val unitPriceCents: Int,
    val totalCents: Int
)
