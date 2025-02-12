package org.access.managementsystem.features.pos.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import org.access.managementsystem.R
import org.access.managementsystem.features.pos.POSScreenViewModel

@Composable
fun ClearOrderDialog(vm: POSScreenViewModel) {
    if (vm.showClearDialog) {
        AlertDialog(
            onDismissRequest = { vm.showClearDialog = false },
            title = { Text("Clear Order") },
            text = { Text("Are you sure you want to clear the order?") },
            confirmButton = {
                IconButton(
                    onClick = {
                        vm.showQrScanner = true
                        vm.showClearDialog = false
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_qr),
                        contentDescription = "QR Code",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { vm.showClearDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
