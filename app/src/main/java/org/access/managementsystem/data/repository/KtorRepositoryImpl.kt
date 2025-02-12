package org.access.managementsystem.data.repository

import org.access.managementsystem.domain.models.JsonResponse
import org.access.managementsystem.domain.models.PreferenceKey
import org.access.managementsystem.domain.models.ResponseType
import org.access.managementsystem.domain.repository.LocalRepository
import org.access.managementsystem.domain.repository.RemoteRepository
import org.access.managementsystem.network.ApiService

class KtorRepositoryImpl(
    private val apiService: ApiService,
    private val localRepository: LocalRepository
) : RemoteRepository {

    override suspend fun login(username: String, password: String): JsonResponse {
        return apiService.login(username, password)
    }

    override suspend fun logout(loginToken: String): JsonResponse {
        return apiService.logout(loginToken)
    }

    override suspend fun getProducts(): Triple<Boolean, JsonResponse, JsonResponse> {
        val loginToken = localRepository.getPreference(PreferenceKey.LOGIN_TOKEN)!!.value
        val productCategories = apiService.getProductCategories(loginToken)
        val products = apiService.getProducts(loginToken)

        return if (products.responseType == ResponseType.ERROR || productCategories.responseType == ResponseType.ERROR) {
            Triple(false, products, productCategories)
        } else {
            Triple(true, products, productCategories)
        }
    }

    override suspend fun sendOrder() {

    }

    override suspend fun getRole(): JsonResponse {
        val loginToken = localRepository.getPreference(PreferenceKey.LOGIN_TOKEN)!!.value
        return apiService.getRole(loginToken)
    }
}