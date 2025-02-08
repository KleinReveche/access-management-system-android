package org.access.managementsystempos.features.pos

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.access.managementsystempos.domain.models.Order
import org.access.managementsystempos.domain.models.Product

class POSScreenViewModel : ViewModel() {
    val cart = mutableStateMapOf<Product, Int>()
    private val orders = mutableStateMapOf<String, Order>() // Store orders by their ID
    private val timers = mutableStateMapOf<String, Boolean>() // Track which order is being timed

    fun addToCart(product: Product) {
        cart[product] = cart[product]?.plus(1) ?: 1
    }

    fun removeFromCart(product: Product) {
        cart[product] = cart[product]?.minus(1) ?: 0
    }

    fun clearCart() {
        cart.clear()
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

