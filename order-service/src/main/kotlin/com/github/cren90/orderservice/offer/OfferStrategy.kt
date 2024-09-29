package com.github.cren90.orderservice.offer

import com.github.cren90.orderservice.items.dto.request.RequestItem

interface OfferStrategy {
    fun calculateDiscount(items: List<RequestItem>): Int
}