package org.access.managementsystempos.domain.repository

import org.access.managementsystempos.domain.models.JsonResponse

interface RemoteRepository {
    suspend fun login(username: String, password: String): JsonResponse
    suspend fun logout(loginToken: String): JsonResponse
    suspend fun getProducts(): Triple<Boolean, JsonResponse, JsonResponse>
    suspend fun sendOrder()
    suspend fun getRole(): JsonResponse
}