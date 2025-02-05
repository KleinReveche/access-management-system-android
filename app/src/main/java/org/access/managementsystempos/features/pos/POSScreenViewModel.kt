package org.access.managementsystempos.features.pos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.access.managementsystempos.domain.models.Product

class POSScreenViewModel : ViewModel() {
    var selectedCategory by mutableIntStateOf(-1)
    val cart = mutableStateMapOf<Product, Int>()

    fun addToCart(product: Product) {
        cart[product] = cart[product]?.plus(1) ?: 1
    }

    fun removeFromCart(product: Product) {
        cart[product] = cart[product]?.minus(1) ?: 0
    }
}