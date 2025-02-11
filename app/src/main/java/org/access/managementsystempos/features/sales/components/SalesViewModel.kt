package org.access.managementsystempos.features.sales.components

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import org.access.managementsystempos.domain.models.Order

class SalesViewModel : ViewModel() {
    private val _sales = mutableStateListOf<Order>()
    val sales: List<Order> get() = _sales

    fun addSale(order: Order) {
        _sales.add(order)
    }
}