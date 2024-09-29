package  com.github.cren90.orderservice.order.domain

import com.github.cren90.orderservice.ERROR_DUPLICATE_ITEM
import com.github.cren90.orderservice.ERROR_NEGATIVE_QUANTITY
import com.github.cren90.orderservice.Result
import com.github.cren90.orderservice.items.Item
import com.github.cren90.orderservice.items.dto.request.RequestItem
import com.github.cren90.orderservice.order.dto.response.OrderResponse
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class OrderProcessorTest {
    val orderProcessor = OrderProcessor()

    @Test
    fun testOrderWithDuplicateItems() {
        val items = listOf(
            RequestItem(Item.APPLE, 1),
            RequestItem(Item.ORANGE, 1),
            RequestItem(Item.APPLE, 1)
        )
        val expectedMessage = ERROR_DUPLICATE_ITEM

        val result = orderProcessor.processOrder(items)

        assertIs<Result.RequestError<OrderResponse>>(result)
        assertEquals(expectedMessage, result.message)
    }

    @Test
    fun testOrderWithNegativeQuantity() {
        val items = listOf(
            RequestItem(Item.APPLE, -1),
            RequestItem(Item.ORANGE, 1)
        )
        val expectedMessage = ERROR_NEGATIVE_QUANTITY

        val result = orderProcessor.processOrder(items)

        assertIs<Result.RequestError<OrderResponse>>(result)
        assertEquals(expectedMessage, result.message)
    }

    @Test
    fun testValidOrder() {
        val appleQuantity = 1
        val orangeQuantity = 5
        val items = listOf(
            RequestItem(Item.APPLE, appleQuantity),
            RequestItem(Item.ORANGE, orangeQuantity)
        )
        val expectedTotal = Item.APPLE.priceCents * appleQuantity + Item.ORANGE.priceCents * orangeQuantity

        val result = orderProcessor.processOrder(items)

        assertIs<Result.Success<OrderResponse>>(result)
        assertEquals(expectedTotal, result.value.totalCents)
    }
}