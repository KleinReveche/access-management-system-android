package org.access.managementsystempos.features.kitchen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable
import org.access.managementsystempos.features.kitchen.components.KitchenOrderItem
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.SharedViewModel


@Composable
fun KitchenScreen(sharedViewModel: SharedViewModel) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("Kitchen", style = MaterialTheme.typography.headlineMedium)

            LazyColumn {
                items(sharedViewModel.orders.values.toList()) { order ->
                    KitchenOrderItem(
                        order = order,
                        onOrderCompleted = {
                            sharedViewModel.markOrderCompleted(order.id)
                        },
                        onClearOrder = {
                            sharedViewModel.removeOrder(order.id)
                        }
                    )
                }
            }
        }
    }
}


@Serializable
object KitchenDestination : ScreenDestination