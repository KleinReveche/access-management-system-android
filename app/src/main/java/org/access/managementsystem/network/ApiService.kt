package org.access.managementsystem.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import org.access.managementsystem.BuildConfig
import org.access.managementsystem.domain.models.JsonResponse
import org.access.managementsystem.domain.models.LoginRequest
import org.access.managementsystem.domain.models.RequestType
import org.access.managementsystem.domain.models.ResponseType
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
class ApiService(private val httpClient: HttpClient) {
    private suspend fun makeRequest(endpoint: String, token: String? = null): HttpResponse {
        return httpClient.get {
            url(endpoint)
            token?.let { header("Token", it) }
        }
    }

    suspend fun login(username: String, password: String): JsonResponse {
        val loginRequest = Json.encodeToString(LoginRequest(username, password))
        val endpoint =
            "${BuildConfig.BASE_URL}api/v1.php?request=LOGIN&data=${
                Base64.UrlSafe.encode(
                    loginRequest.toByteArray()
                )
            }"
        return try {
            val response = makeRequest(endpoint)
            Json.decodeFromString(response.bodyAsText())
        } catch (e: Exception) {
            JsonResponse(
                requestType = RequestType.LOGIN,
                responseType = ResponseType.ERROR,
                message = "Error: ${e.message}"
            )
        }
    }

    suspend fun logout(loginToken: String): JsonResponse {
        val endpoint = "${BuildConfig.BASE_URL}api/v1.php?request=LOGOUT&data=$loginToken"
        return try {
            val response = makeRequest(endpoint)
            Json.decodeFromString(response.bodyAsText())
        } catch (e: Exception) {
            JsonResponse(
                requestType = RequestType.LOGOUT,
                responseType = ResponseType.ERROR,
                message = "Error: ${e.message}"
            )
        }
    }

    suspend fun getProducts(loginToken: String): JsonResponse {
        val endpoint = "${BuildConfig.BASE_URL}api/v1.php?request=GET_PRODUCTS&data=gpd"
        return Json.decodeFromString<JsonResponse>(makeRequest(endpoint, loginToken).bodyAsText())
    }

    suspend fun getProductCategories(loginToken: String): JsonResponse {
        val endpoint = "${BuildConfig.BASE_URL}api/v1.php?request=GET_PRODUCT_CATEGORIES&data=gpc"
        return Json.decodeFromString<JsonResponse>(makeRequest(endpoint, loginToken).bodyAsText())
    }

    suspend fun getRole(loginToken: String): JsonResponse {
        val endpoint = "${BuildConfig.BASE_URL}api/v1.php?request=GET_ROLE&data=gr"
        return Json.decodeFromString<JsonResponse>(makeRequest(endpoint, loginToken).bodyAsText())
    }
}