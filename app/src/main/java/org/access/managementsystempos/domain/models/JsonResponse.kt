package org.access.managementsystempos.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class JsonResponse(
    val requestType: RequestType,
    val responseType: ResponseType,
    val message: String,
)

@Serializable
enum class ResponseType {
    SUCCESS,
    ERROR
}

@Serializable
enum class RequestType {
    HELLO_WORLD,
    VOUCHER,
    OTHER,
    LOGIN,
    LOGOUT,
    GET_PUBLIC_KEY,
    GET_ROLE,
    GET_PRODUCTS,
    GET_PRODUCT_CATEGORIES,
}