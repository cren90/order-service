package com.github.cren90.orderservice.order.domain

import com.github.cren90.orderservice.ERROR_DUPLICATE_ITEM
import com.github.cren90.orderservice.ERROR_NEGATIVE_QUANTITY
import com.github.cren90.orderservice.Result
import com.github.cren90.orderservice.items.dto.request.RequestItem
import com.github.cren90.orderservice.items.dto.response.ResponseItem
import com.github.cren90.orderservice.order.dto.response.OrderResponse
import java.util.*

class OrderProcessor {
    fun processOrder(orderItems: List<RequestItem>): Result<OrderResponse> {
        if (hasDuplicateItems(orderItems)) {
            return Result.RequestError(ERROR_DUPLICATE_ITEM)
        }

        if (hasNegativeQuantity(orderItems)) {
            return Result.RequestError(ERROR_NEGATIVE_QUANTITY)
        }

        val itemTotals = mutableMapOf<RequestItem, Int>()
        orderItems.forEach {
            itemTotals[it] = calculateItemTotal(it)
        }

        val total = calculateTotal(itemTotals.values)

        return Result.Success(createOrderResponse(itemTotals, total))
    }

    private fun hasDuplicateItems(orderItems: List<RequestItem>): Boolean {
        val set = orderItems.map { it.item }.toSet()
        return set.size != orderItems.size
    }

    private fun hasNegativeQuantity(orderItems: List<RequestItem>): Boolean {
        return orderItems.any { it.quantity < 0 }
    }

    private fun calculateItemTotal(item: RequestItem): Int {
        return item.item.priceCents * item.quantity
    }

    private fun calculateTotal(itemTotals: Collection<Int>): Int {
        return itemTotals.sum()
    }

    private fun createOrderResponse(itemTotals: Map<RequestItem, Int>, total: Int): OrderResponse {
        return OrderResponse(
            orderId = UUID.randomUUID().toString(),
            orderItems = itemTotals.map { itemTotal ->
                ResponseItem(
                    item = itemTotal.key.item,
                    quantity = itemTotal.key.quantity,
                    unitPriceCents = itemTotal.key.item.priceCents,
                    totalCents = itemTotal.value
                )
            },
            totalCents = total
        )
    }
}
