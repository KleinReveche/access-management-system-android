package org.access.managementsystempos.data.repository

import org.access.managementsystempos.domain.models.JsonResponse
import org.access.managementsystempos.domain.models.PreferenceKey
import org.access.managementsystempos.domain.models.ResponseType
import org.access.managementsystempos.domain.repository.LocalRepository
import org.access.managementsystempos.domain.repository.RemoteRepository
import org.access.managementsystempos.network.ApiService

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