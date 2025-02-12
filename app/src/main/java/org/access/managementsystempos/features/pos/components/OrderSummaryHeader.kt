package org.access.managementsystempos.features.pos.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.access.managementsystempos.R
import org.access.managementsystempos.features.pos.POSScreenViewModel

@Composable
fun OrderSummaryHeader(vm: POSScreenViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Order Summary", style = MaterialTheme.typography.headlineMedium)

        IconButton(
            onClick = { vm.showClearDialog = true },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_clear),
                contentDescription = "Clear Order",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}