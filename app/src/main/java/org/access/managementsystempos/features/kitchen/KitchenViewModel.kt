package org.access.managementsystempos.features.kitchen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import org.access.managementsystempos.domain.models.Order

class KitchenViewModel : ViewModel() {
    private val _orders = mutableStateListOf<Order>()
    val orders: List<Order> get() = _orders

    fun addOrder(order: Order) {
        _orders.add(order)
    }

    fun removeOrder(order: Order) {
        _orders.remove(order)
    }
}