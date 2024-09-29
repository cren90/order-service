package com.github.cren90.orderservice.order.domain

import com.github.cren90.orderservice.ERROR_INVALID_ORDER_ID
import com.github.cren90.orderservice.ERROR_ORDER_NOT_FOUND
import com.github.cren90.orderservice.Result
import com.github.cren90.orderservice.order.dto.response.OrderResponse
import com.github.cren90.orderservice.order.dto.response.OrderResponseList
import com.github.cren90.orderservice.order.repository.OrderRepository
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class OrderRetrieverTest {

    private val orderRepo = object : OrderRepository {
        private val orders = getTestOrderMap()

        override fun saveOrder(orderResponse: OrderResponse) {}

        override fun getOrder(id: String): OrderResponse? {
            return orders[id]
        }

        override fun getAllOrders(): List<OrderResponse> {
            return orders.values.toList()
        }
    }
    private val orderRetriever = OrderRetriever(orderRepo)

    @Test
    fun testGetAllOrdersShouldReturnOrderListOnSuccess() {

        val result = orderRetriever.getAllOrders()

        assertIs<Result.Success<OrderResponseList>>(result)
        assertEquals(result.value.orders.size, getTestOrderMap().size)
    }

    @Test
    fun testGetOrderByIdShouldReturnOrderOnSuccess() {
        val orderId = "2"
        val result = orderRetriever.getOrderById(orderId)

        assertIs<Result.Success<OrderResponse>>(result)
        assertEquals(result.value.orderId, orderId)
    }

    @Test
    fun testGetOrderByIdShouldReturnRequestErrorWhenOrderIdIsNull() {
        val orderId: String? = null

        val result = orderRetriever.getOrderById(orderId)

        assertEquals(Result.RequestError(ERROR_INVALID_ORDER_ID), result)
    }

    @Test
    fun testGetOrderByIdShouldReturnNotFoundErrorWhenOrderNotFound() {
        val orderId = "3"

        val result = orderRetriever.getOrderById(orderId)

        assertEquals(Result.NotFoundError(ERROR_ORDER_NOT_FOUND), result)
    }
}