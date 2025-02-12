package org.access.managementsystem.features.kitchen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import org.access.managementsystem.domain.models.Order
import org.access.managementsystem.features.sales.components.SalesViewModel

class KitchenViewModel : ViewModel() {
    private val _orders = mutableStateListOf<Order>()
    val orders: List<Order> get() = _orders

    private val _completedOrders = mutableStateListOf<Order>()
    val completedOrders: List<Order> get() = _completedOrders

    fun addOrder(order: Order) {
        _orders.add(order)
    }

    fun removeOrder(order: Order) {
        _orders.remove(order)
    }

    fun markOrderCompleted(orderId: String, salesViewModel: SalesViewModel) {
        val order = _orders.find { it.id == orderId }
        order?.let {
            _orders.remove(it)
            _completedOrders.add(it.copy(isCompleted = true))
            salesViewModel.addSale(it) // Send order to Sales
        }
    }
}