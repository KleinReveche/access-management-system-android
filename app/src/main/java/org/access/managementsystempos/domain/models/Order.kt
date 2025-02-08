package org.access.managementsystempos.domain.models


data class Order(
    val id: String = Order.generateOrderId(),
    val items: Map<Product, Int>,
    var isCompleted: Boolean = false,
    var elapsedTime: Int = 0
) {
    companion object {
        private var orderCounter = 1

        fun generateOrderId(): String {
            return "ORD-${orderCounter++}"
        }
    }
}
