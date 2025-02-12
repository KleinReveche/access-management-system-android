package org.access.managementsystem.features.pos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystem.features.navigation.ScreenDestination
import org.access.managementsystem.features.pos.components.CategoryFilter
import org.access.managementsystem.features.pos.components.CheckoutButton
import org.access.managementsystem.features.pos.components.ClearOrderDialog
import org.access.managementsystem.features.pos.components.OrderBottomSheet
import org.access.managementsystem.features.pos.components.POSAppBar
import org.access.managementsystem.features.pos.components.ProductGrid
import org.access.managementsystem.features.pos.components.QRScannerDialog
import org.koin.androidx.compose.koinViewModel


@Composable
fun POSScreen(navController: NavController) {
    val vm: POSScreenViewModel = koinViewModel()

    Scaffold(
        topBar = { POSAppBar(navController) },
        bottomBar = { CheckoutButton(vm) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            CategoryFilter(vm)
            Spacer(modifier = Modifier.height(16.dp))
            ProductGrid(vm)
        }
    }

    OrderBottomSheet(vm, navController)
    ClearOrderDialog(vm)
    QRScannerDialog(vm)
}
@Serializable
object POSScreenDestination : ScreenDestination