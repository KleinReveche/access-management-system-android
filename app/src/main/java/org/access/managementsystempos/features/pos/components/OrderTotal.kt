package org.access.managementsystempos.features.pos.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.access.managementsystempos.features.pos.POSScreenViewModel

@Composable
fun OrderTotal(vm: POSScreenViewModel, navController: NavController) {
    if (vm.cart.isNotEmpty()) {
        val totalPrice =
            vm.cart.entries.sumOf { (product, quantity) -> product.price.toDouble() * quantity }
                .toFloat()

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total:", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "₱${"%.2f".format(totalPrice)}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("cart", HashMap(vm.cart))
                navController.navigate("checkout_screen/${totalPrice.toString().replace(",", ".")}")
                vm.clearCart()
                vm.showBottomSheet = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send to Checkout")
        }
    }
}