package org.access.managementsystem.features.pos.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.access.managementsystem.features.pos.POSScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderBottomSheet(vm: POSScreenViewModel, navController: NavController) {
    if (vm.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { vm.showBottomSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OrderSummaryHeader(vm)
                Spacer(modifier = Modifier.height(16.dp))
                OrderSummary(vm.cart)
                OrderTotal(vm, navController)
            }
        }
    }
}