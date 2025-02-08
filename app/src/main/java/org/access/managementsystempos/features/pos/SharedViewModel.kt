package org.access.managementsystempos.features.pos


import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.access.managementsystempos.domain.models.Order

class SharedViewModel : ViewModel() {
    private val _orders = mutableStateMapOf<String, Order>()
    val orders: Map<String, Order> get() = _orders

    private val timers = mutableMapOf<String, Job>()
    private var orderCounter = 1

    fun addOrder(order: Order) {
        val orderId = "ORD-${orderCounter++}"
        val newOrder = order.copy(id = orderId)
        _orders[orderId] = newOrder
        startTimerForOrder(orderId)
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
            _orders[orderId] = order.copy(isCompleted = true, elapsedTime = 0)
            stopTimerForOrder(orderId)
        }
    }

    fun removeOrder(orderId: String) {
        stopTimerForOrder(orderId)
        _orders.remove(orderId)
    }
}
