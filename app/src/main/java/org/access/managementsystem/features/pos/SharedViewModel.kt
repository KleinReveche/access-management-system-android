package org.access.managementsystem.features.pos


import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.access.managementsystem.domain.models.Order

class SharedViewModel : ViewModel() {
    private val _orders = mutableStateMapOf<String, Order>()
    val orders: Map<String, Order> get() = _orders

    private val _completedOrders = mutableStateMapOf<String, Order>()
    val completedOrders: Map<String, Order> get() = _completedOrders

    private val timers = mutableMapOf<String, Job>()
    private var orderCounter = 1

    fun addOrder(order: Order) {
        val newOrder = order.copy(id = Order.generateOrderId())
        _orders[newOrder.id] = newOrder
        startTimerForOrder(newOrder.id)
    }

    private fun startTimerForOrder(orderId: String) {
        if (timers.containsKey(orderId)) return

        timers[orderId] = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _orders[orderId]?.let { order ->
                    _orders[orderId] = order.copy(elapsedTime = order.elapsedTime + 1)
                }
            }
        }
    }

    fun stopTimerForOrder(orderId: String) {
        timers[orderId]?.cancel()
        timers.remove(orderId)
    }

    fun markOrderCompleted(orderId: String) {
        _orders[orderId]?.let { order ->
            val completedOrder = order.copy(isCompleted = true)
            _completedOrders[orderId] = completedOrder
            removeOrder(orderId)
        }
    }

    fun removeOrder(orderId: String) {
        stopTimerForOrder(orderId)
        _orders.remove(orderId)
    }
}