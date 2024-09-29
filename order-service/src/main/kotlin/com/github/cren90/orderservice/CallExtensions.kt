package com.github.cren90.orderservice

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

suspend inline fun <reified T : Any> RoutingCall.sendResultResponse(
    result: Result<T>,
    successStatusCode: HttpStatusCode
) {
    when (result) {
        is Result.RequestError -> respondText(status = HttpStatusCode.BadRequest) { result.message }
        is Result.ServerError -> respondText(status = HttpStatusCode.InternalServerError) { result.message }
        is Result.NotFoundError -> respondText(status = HttpStatusCode.NotFound) { result.message }
        is Result.Success -> respond(status = successStatusCode, message = result.value)
    }
}