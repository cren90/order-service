package com.github.cren90.orderservice.offer

import com.github.cren90.orderservice.items.Item
import com.github.cren90.orderservice.items.dto.request.RequestItem
import org.junit.Test
import kotlin.test.assertEquals

class BuyGetOfferTests {

    @Test
    fun testBuyOneGetOneApples_NoApplesShouldDiscount0Cents() {
        val offerStrategy = BuyGetOffer(Item.APPLE, 1, 1)
        val items = listOf(
            RequestItem(Item.ORANGE, 1)
        )
        val expectedDiscount = 0

        assertEquals(expectedDiscount, offerStrategy.calculateDiscount(items))
    }

    @Test
    fun testBuyOneGetOneApples_ZeroApplesShouldDiscount0Cents() {
        val offerStrategy = BuyGetOffer(Item.APPLE, 1, 1)
        val items = listOf(
            RequestItem(Item.APPLE, 0),
            RequestItem(Item.ORANGE, 1)
        )
        val expectedDiscount = 0

        assertEquals(expectedDiscount, offerStrategy.calculateDiscount(items))
    }

    @Test
    fun testBuyOneGetOneApples_OneAppleShouldDiscount0Cents() {
        val offerStrategy = BuyGetOffer(Item.APPLE, 1, 1)
        val items = listOf(
            RequestItem(Item.APPLE, 1),
            RequestItem(Item.ORANGE, 1)
        )
        val expectedDiscount = 0

        assertEquals(expectedDiscount, offerStrategy.calculateDiscount(items))
    }

    @Test
    fun testBuyOneGetOneApples_TwoAppleShouldDiscountOneAppleCost() {
        val offerStrategy = BuyGetOffer(Item.APPLE, 1, 1)
        val items = listOf(
            RequestItem(Item.APPLE, 2),
            RequestItem(Item.ORANGE, 1)
        )
        val expectedDiscount = Item.APPLE.priceCents

        assertEquals(expectedDiscount, offerStrategy.calculateDiscount(items))
    }

    @Test
    fun testBuyOneGetOneApples_FourAppleShouldDiscountTwoAppleCost() {
        val offerStrategy = BuyGetOffer(Item.APPLE, 1, 1)
        val items = listOf(
            RequestItem(Item.APPLE, 4),
            RequestItem(Item.ORANGE, 1)
        )
        val expectedDiscount = 2 * Item.APPLE.priceCents

        assertEquals(expectedDiscount, offerStrategy.calculateDiscount(items))
    }

    @Test
    fun testBuy3PriceOf2Oranges_3OrangesShouldDiscountOneOrangeCost() {
        val offerStrategy = BuyGetOffer(Item.ORANGE, 2, 1)
        val items = listOf(
            RequestItem(Item.ORANGE, 3)
        )
        val expectedDiscount = Item.ORANGE.priceCents

        assertEquals(expectedDiscount, offerStrategy.calculateDiscount(items))
    }

    @Test
    fun testBuy3PriceOf2Oranges_7OrangesShouldDiscountTwoOrangeCost() {
        val offerStrategy = BuyGetOffer(Item.ORANGE, 2, 1)
        val items = listOf(
            RequestItem(Item.ORANGE, 7)
        )
        val expectedDiscount = 2 * Item.ORANGE.priceCents

        assertEquals(expectedDiscount, offerStrategy.calculateDiscount(items))
    }
}