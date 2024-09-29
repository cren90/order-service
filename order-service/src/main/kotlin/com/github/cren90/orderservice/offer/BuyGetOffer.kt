package com.github.cren90.orderservice.offer

import com.github.cren90.orderservice.items.Item
import com.github.cren90.orderservice.items.dto.request.RequestItem

/**
 * Calculates the discount on the provided item for the buy and get count.
 * Examples:
 * Buy One, Get One Apples -> BuyGetOffer(Item.Apple, 1, 1)
 * 3 For price of 2 Oranges -> BuyGetOffer(Item.Orange, 2, 1)
 */
class BuyGetOffer(val item: Item, val buyCount: Int, val getCount: Int) : OfferStrategy {
    override fun calculateDiscount(items: List<RequestItem>): Int {
        val filteredItems = items.firstOrNull { it.item == item }

        val offerMultiple = buyCount + getCount

        return when {
            filteredItems?.quantity == null ||
                    filteredItems.quantity < offerMultiple -> 0

            else -> {
                val freeItemCount = filteredItems.quantity / offerMultiple
                //Returned:
                freeItemCount * filteredItems.item.priceCents
            }
        }
    }

}