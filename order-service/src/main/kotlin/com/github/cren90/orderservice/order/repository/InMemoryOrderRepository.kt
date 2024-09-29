package com.github.cren90.orderservice.order.repository

import com.github.cren90.orderservice.order.dto.response.OrderResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object InMemoryOrderRepository : OrderRepository {
    val orders: MutableMap<String, OrderResponse> = mutableMapOf()

    val ordersMutex = Mutex()

    override fun saveOrder(orderResponse: OrderResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            ordersMutex.withLock {
                orders[orderResponse.orderId] = orderResponse
            }
        }
    }

    override fun getOrder(id: String): OrderResponse? {
        return runBlocking(Dispatchers.IO) {
            ordersMutex.withLock {
                orders[id]
            }
        }
    }

    override fun getAllOrders(): List<OrderResponse> {
        return runBlocking(Dispatchers.IO) {
            ordersMutex.withLock {
                orders.values.toList()
            }
        }
    }
}