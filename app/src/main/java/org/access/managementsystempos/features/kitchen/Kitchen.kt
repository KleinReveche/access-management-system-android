package org.access.managementsystempos.features.kitchen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable
import org.access.managementsystempos.features.navigation.ScreenDestination

@Composable
fun KitchenScreen() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("Kitchen")
        }
    }
}

@Serializable
object KitchenDestination : ScreenDestination