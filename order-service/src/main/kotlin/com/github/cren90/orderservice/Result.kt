package com.github.cren90.orderservice

sealed interface Result<T> {
    data class Success<T>(val value: T) : Result<T>
    data class RequestError<T>(val message: String) : Result<T>
    data class NotFoundError<T>(val message: String) : Result<T>
    data class ServerError<T>(val message: String) : Result<T>
}

const val ERROR_DUPLICATE_ITEM = "Order has duplicate items"
const val ERROR_NEGATIVE_QUANTITY = "Quantities must be non-negative"
const val ERROR_ORDER_NOT_FOUND = "Order was not found matching the order id specified"
const val ERROR_INVALID_ORDER_ID = "An invalid order id was specified"