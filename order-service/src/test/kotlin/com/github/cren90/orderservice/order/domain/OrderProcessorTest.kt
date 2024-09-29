package  com.github.cren90.orderservice.order.domain

import com.github.cren90.orderservice.ERROR_DUPLICATE_ITEM
import com.github.cren90.orderservice.ERROR_NEGATIVE_QUANTITY
import com.github.cren90.orderservice.Result
import com.github.cren90.orderservice.items.Item
import com.github.cren90.orderservice.items.dto.request.RequestItem
import com.github.cren90.orderservice.offer.BuyGetOffer
import com.github.cren90.orderservice.offer.OfferStrategy
import com.github.cren90.orderservice.offer.repository.OfferRepository
import com.github.cren90.orderservice.order.dto.response.OrderResponse
import com.github.cren90.orderservice.order.repository.InMemoryOrderRepository
import com.github.cren90.orderservice.order.repository.OrderRepository
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class OrderProcessorTest {

    private val emptyOfferRepo = object : OfferRepository {
        override fun getCurrentOffers(): List<OfferStrategy> = emptyList()
    }

    private val testOfferRepo = object : OfferRepository {
        override fun getCurrentOffers(): List<OfferStrategy> = listOf(
            BuyGetOffer(Item.APPLE, 1, 1), //BOGO Apples
            BuyGetOffer(Item.ORANGE, 2, 1) // 3 for price of 2 Oranges
        )
    }

    private val noopOrderRepo = object : OrderRepository {
        override fun saveOrder(orderResponse: OrderResponse) {}

        override fun getOrder(id: String): OrderResponse? = null

        override fun getAllOrders(): List<OrderResponse> = emptyList()
    }

    @Test
    fun testOrderWithDuplicateItems() {
        val orderProcessor = OrderProcessor(emptyOfferRepo, noopOrderRepo)
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
        val orderProcessor = OrderProcessor(emptyOfferRepo, noopOrderRepo)
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
        val orderProcessor = OrderProcessor(emptyOfferRepo, noopOrderRepo)
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

    @Test
    fun testValidOrderWithOffers_NoOfferApplied() {
        val orderProcessor = OrderProcessor(testOfferRepo, noopOrderRepo)
        val appleQuantity = 1
        val orangeQuantity = 1

        val items = listOf(
            RequestItem(Item.APPLE, appleQuantity),
            RequestItem(Item.ORANGE, orangeQuantity)
        )

        val expectedTotal = Item.APPLE.priceCents * appleQuantity + Item.ORANGE.priceCents * orangeQuantity

        val result = orderProcessor.processOrder(items)

        assertIs<Result.Success<OrderResponse>>(result)
        assertEquals(expectedTotal, result.value.totalCents)

    }

    @Test
    fun testValidOrderWithOffers_AppleOfferApplied() {
        val orderProcessor = OrderProcessor(testOfferRepo, noopOrderRepo)
        val appleQuantity = 2
        val orangeQuantity = 1

        val items = listOf(
            RequestItem(Item.APPLE, appleQuantity),
            RequestItem(Item.ORANGE, orangeQuantity)
        )

        val expectedTotal = Item.APPLE.priceCents * appleQuantity / 2 + Item.ORANGE.priceCents * orangeQuantity

        val result = orderProcessor.processOrder(items)

        assertIs<Result.Success<OrderResponse>>(result)
        assertEquals(expectedTotal, result.value.totalCents)

    }

    @Test
    fun testValidOrderWithOffers_OrangeOfferApplied() {
        val orderProcessor = OrderProcessor(testOfferRepo, noopOrderRepo)
        val appleQuantity = 1
        val orangeQuantity = 3

        val items = listOf(
            RequestItem(Item.APPLE, appleQuantity),
            RequestItem(Item.ORANGE, orangeQuantity)
        )

        val expectedTotal = Item.APPLE.priceCents * appleQuantity + Item.ORANGE.priceCents * orangeQuantity * 2 / 3

        val result = orderProcessor.processOrder(items)

        assertIs<Result.Success<OrderResponse>>(result)
        assertEquals(expectedTotal, result.value.totalCents)
    }

    @Test
    fun testValidOrderWithOffers_AppleAndOrangeOfferApplied() {
        val orderProcessor = OrderProcessor(testOfferRepo, noopOrderRepo)
        val appleQuantity = 2
        val orangeQuantity = 3

        val items = listOf(
            RequestItem(Item.APPLE, appleQuantity),
            RequestItem(Item.ORANGE, orangeQuantity)
        )

        val expectedTotal = Item.APPLE.priceCents * appleQuantity / 2 + Item.ORANGE.priceCents * orangeQuantity * 2 / 3

        val result = orderProcessor.processOrder(items)

        assertIs<Result.Success<OrderResponse>>(result)
        assertEquals(expectedTotal, result.value.totalCents)
    }

    @Test
    fun testValidOrderSavesToOrderRepo() {
        val orderRepo = InMemoryOrderRepository
        val orderProcessor = OrderProcessor(testOfferRepo, orderRepo)

        val appleQuantity = 2
        val orangeQuantity = 3

        val items = listOf(
            RequestItem(Item.APPLE, appleQuantity),
            RequestItem(Item.ORANGE, orangeQuantity)
        )

        val result = orderProcessor.processOrder(items)

        assertIs<Result.Success<OrderResponse>>(result)

        val orderFromRepo = orderRepo.getOrder(result.value.orderId)
        assertEquals(result.value.orderId, orderFromRepo?.orderId)

    }

}