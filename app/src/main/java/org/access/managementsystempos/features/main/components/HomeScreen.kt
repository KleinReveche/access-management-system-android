package org.access.managementsystempos.features.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.access.managementsystempos.R
import org.access.managementsystempos.features.kitchen.KitchenDestination
import org.access.managementsystempos.features.pos.POSScreenDestination

@Composable
fun HomeScreen(navController: NavController) {
    val screens = listOf(
        Triple("POS", POSScreenDestination, R.drawable.ic_pos),
        Triple("Kitchen", KitchenDestination, R.drawable.ic_kitchen)
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(screens.size) { index ->
            val (text, destination, iconRes) = screens[index]
            ElevatedCardButton(text, iconRes) {
                navController.navigate(destination)
            }
        }
    }
}

@Composable
fun ElevatedCardButton(text: String, iconRes: Int, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}