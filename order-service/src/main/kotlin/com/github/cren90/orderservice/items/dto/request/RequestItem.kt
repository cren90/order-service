package com.github.cren90.orderservice.items.dto.request

import com.github.cren90.orderservice.items.Item
import kotlinx.serialization.Serializable

@Serializable
data class RequestItem(
    val item: Item,
    val quantity: Int
)
