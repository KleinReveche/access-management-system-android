package org.access.managementsystem.features.pos.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.access.managementsystem.features.pos.POSScreenViewModel

@Composable
fun CheckoutButton(vm: POSScreenViewModel) {
    val context = LocalContext.current

    Box(modifier = Modifier.padding(16.dp)) {
        BadgedBox(
            badge = {
                if (vm.cart.isNotEmpty()) {
                    Badge { Text(vm.cart.values.sum().toString()) }
                }
            }
        ) {
            Button(
                onClick = { vm.showBottomSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = vm.cart.isNotEmpty()
            ) {
                Text("Checkout")
            }
        }
    }
}
