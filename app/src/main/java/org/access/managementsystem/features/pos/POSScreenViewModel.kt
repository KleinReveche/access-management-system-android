package org.access.managementsystem.features.pos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.access.managementsystem.domain.models.Order
import org.access.managementsystem.domain.models.Product
import org.access.managementsystem.domain.models.ProductCategory
import org.access.managementsystem.domain.repository.LocalRepository

class POSScreenViewModel(localRepository: LocalRepository) : ViewModel() {
    val productCategories = mutableStateListOf<ProductCategory>()
    val products = mutableStateListOf<Product>()
    val cart = mutableStateMapOf<Product, Int>()
    private val orders = mutableStateMapOf<String, Order>() // Store orders by their ID
    private val timers = mutableStateMapOf<String, Boolean>() // Track which order is being timed
    var showQrScanner by mutableStateOf(false)
    var selectedCategory by mutableStateOf<ProductCategory?>(null)
    var showBottomSheet by mutableStateOf(false)
    var showClearDialog by mutableStateOf(false)

    init {
        viewModelScope.launch {
            productCategories.addAll(localRepository.getProductCategories())
            products.addAll(localRepository.getProducts())
        }
    }

    fun addToCart(product: Product) {
        cart[product] = cart[product]?.plus(1) ?: 1
    }

    fun removeFromCart(product: Product) {
        cart[product] = cart[product]?.minus(1) ?: 0
    }

    fun clearCart() {
        cart.clear()
    }

    fun verifyMasterKey(masterKey: String): Boolean {
        return masterKey == "1234"
    }

    fun startTimerForOrder(orderId: String) {
        if (!timers.contains(orderId) || timers[orderId] == false) {
            timers[orderId] = true
            viewModelScope.launch {
                while (timers[orderId] == true) {
                    delay(1000)
                    orders[orderId]?.elapsedTime = orders[orderId]?.elapsedTime?.plus(1) ?: 0
                }
            }
        }
    }

    fun stopTimerForOrder(orderId: String) {
        timers[orderId] = false
    }

    fun resetTimerForOrder(orderId: String) {
        orders[orderId]?.elapsedTime = 0
    }

    fun getOrderById(orderId: String): Order? {
        return orders[orderId]
    }

    fun addOrder(order: Order) {
        orders[order.id] = order
        startTimerForOrder(order.id)
    }
}

