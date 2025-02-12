package org.access.managementsystem.features.common.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.access.managementsystem.features.common.icons.Error

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    errorMessage: String
) {
    SimpleAlertDialog(
        onDismissRequest = onDismissRequest,
        dialogTitle = "Error",
        dialogText = errorMessage,
        icon = Error,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text("OK")
            }
        }
    )
}