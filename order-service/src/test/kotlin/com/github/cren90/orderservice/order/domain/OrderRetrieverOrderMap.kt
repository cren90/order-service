package com.github.cren90.orderservice.order.domain

import com.github.cren90.orderservice.items.Item
import com.github.cren90.orderservice.items.dto.response.ResponseItem
import com.github.cren90.orderservice.order.dto.response.OrderResponse

fun getTestOrderMap() = mutableMapOf(
    "1" to OrderResponse(
        orderId = "1",
        orderItems = listOf(
            ResponseItem(
                item = Item.APPLE,
                quantity = 3,
                unitPriceCents = Item.APPLE.priceCents,
                totalCents = 3 * Item.APPLE.priceCents
            ),
            ResponseItem(
                item = Item.ORANGE,
                quantity = 2,
                unitPriceCents = Item.ORANGE.priceCents,
                totalCents = 2 * Item.ORANGE.priceCents
            )
        ),
        subtotalCents = 3 * Item.APPLE.priceCents + 2 * Item.ORANGE.priceCents,
        discountCents = 1 * Item.APPLE.priceCents,
        totalCents = 2 * Item.APPLE.priceCents + 2 * Item.ORANGE.priceCents
    ),
    "2" to OrderResponse(
        orderId = "2",
        orderItems = listOf(
            ResponseItem(
                item = Item.APPLE,
                quantity = 0,
                unitPriceCents = Item.APPLE.priceCents,
                totalCents = 0
            ),
            ResponseItem(
                item = Item.ORANGE,
                quantity = 2,
                unitPriceCents = Item.ORANGE.priceCents,
                totalCents = 2 * Item.ORANGE.priceCents
            )
        ),
        subtotalCents = 2 * Item.ORANGE.priceCents,
        discountCents = 1 * Item.APPLE.priceCents,
        totalCents = 2 * Item.ORANGE.priceCents
    )
)