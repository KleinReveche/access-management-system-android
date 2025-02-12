package org.access.managementsystempos.domain.models


data class Order(
    val id: String = generateOrderId(),
    val customerName: String = "",
    val gradeProgram: String = "",
    val items: Map<Product, Int> = emptyMap(),
    val paymentType: String = "Cash",
    val paymentProof: String? = null,
    var isCompleted: Boolean = false,
    var elapsedTime: Int = 0
) {
    fun totalPrice(): Float {
        return items.entries.sumOf { (product, quantity) -> (product.price * quantity).toDouble() }
            .toFloat()
    }

    companion object {
        private var orderCounter = 1

        fun generateOrderId(): String {
            orderCounter += 1
            return "ORD-$orderCounter"
        }
    }
}
