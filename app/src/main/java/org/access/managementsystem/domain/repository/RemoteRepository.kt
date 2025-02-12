package org.access.managementsystem.domain.repository

import org.access.managementsystem.domain.models.JsonResponse

interface RemoteRepository {
    suspend fun login(username: String, password: String): JsonResponse
    suspend fun logout(loginToken: String): JsonResponse
    suspend fun getProducts(): Triple<Boolean, JsonResponse, JsonResponse>
    suspend fun sendOrder()
    suspend fun getRole(): JsonResponse
}