package org.access.managementsystempos.features.kitchen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.access.managementsystempos.domain.models.Order

@SuppressLint("DefaultLocale")
@Composable
fun KitchenOrderItem(
    order: Order,
    onOrderCompleted: (Order) -> Unit,
    onClearOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    var elapsedTime by remember { mutableIntStateOf(order.elapsedTime) }

    LaunchedEffect(order.id, order.isCompleted) {
        while (!order.isCompleted) {
            delay(1000L)
            elapsedTime += 1
        }
    }

    val totalPrice = order.items.entries.sumOf { (product, quantity) ->
        product.price.toDouble() * quantity
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Order ID: ${order.id}")

            val itemsText = order.items.entries.joinToString(", ") { (product, quantity) ->
                "${product.name} x$quantity"
            }
            Text("Items: $itemsText")

            Text("Total Price: ₱${"%,.2f".format(totalPrice)}")
            Text("Elapsed Time: ${String.format("%02d:%02d", elapsedTime / 60, elapsedTime % 60)}")
        }

        Checkbox(
            checked = order.isCompleted,
            onCheckedChange = { checked ->
                if (checked) {
                    onOrderCompleted(order)
                }
            }
        )
        IconButton(onClick = { onClearOrder() }) {
            Icon(Icons.Filled.Delete, contentDescription = "Clear Order")
        }
    }
}