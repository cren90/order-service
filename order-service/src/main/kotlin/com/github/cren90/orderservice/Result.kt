package com.github.cren90.orderservice

sealed interface Result<T> {
    data class Success<T>(val value: T) : Result<T>
    data class RequestError<T>(val message: String) : Result<T>
    data class ServerError<T>(val message: String) : Result<T>
}

const val ERROR_DUPLICATE_ITEM = "Order has duplicate items"
const val ERROR_NEGATIVE_QUANTITY = "Quantities must be non-negative"