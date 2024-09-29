package com.github.cren90.orderservice.offer.repository

import com.github.cren90.orderservice.items.Item
import com.github.cren90.orderservice.offer.BuyGetOffer
import com.github.cren90.orderservice.offer.OfferStrategy

object InMemoryOfferRepository : OfferRepository {
    private val currentOffers: List<OfferStrategy> = listOf(
        BuyGetOffer(Item.APPLE, 1, 1), //BOGO Apples
        BuyGetOffer(Item.ORANGE, 2, 1) // 3 for price of 2 Oranges
    )

    override fun getCurrentOffers(): List<OfferStrategy> = currentOffers
}