package com.github.cren90.orderservice.items

import kotlinx.serialization.SerialName

enum class Item(val priceCents: Int) {
    @SerialName("apple")
    APPLE(priceCents = 60),

    @SerialName("orange")
    ORANGE(priceCents = 25)
}