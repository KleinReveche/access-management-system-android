package org.access.managementsystempos.features.pos

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import org.access.managementsystempos.domain.models.Order

class SharedViewModel : ViewModel() {
    var orders = mutableStateListOf<Order>()

    fun addOrder(order: Order) {
        orders.add(order)
    }
}