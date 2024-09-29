package com.github.cren90.orderservice.order.dto.request

import com.github.cren90.orderservice.items.dto.request.RequestItem
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val items: List<RequestItem>
)
