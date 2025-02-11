package org.access.managementsystempos.features.sales

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.R
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.SharedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    cashierName: String
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sales", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary // 🔹 Uses theme color
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            val completedOrders = sharedViewModel.completedOrders.values.toList()

            if (completedOrders.isEmpty()) {
                Text("No completed orders yet.", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn {
                    items(completedOrders) { order ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Order ID: ${order.id}")
                                Text("Cashier: $cashierName")
                                Text("Total Price: ₱${"%,.2f".format(order.totalPrice())}")
                                Text("Items Ordered:")

                                order.items.forEach { (product, quantity) ->
                                    Text("- ${product.name} x$quantity")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object SalesDestination : ScreenDestination