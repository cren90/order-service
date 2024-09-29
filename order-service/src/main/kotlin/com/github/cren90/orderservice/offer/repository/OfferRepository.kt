package com.github.cren90.orderservice.offer.repository

import com.github.cren90.orderservice.offer.OfferStrategy

interface OfferRepository {
    fun getCurrentOffers(): List<OfferStrategy>
}