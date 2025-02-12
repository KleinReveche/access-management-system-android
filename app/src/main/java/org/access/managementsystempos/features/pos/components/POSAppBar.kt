package org.access.managementsystempos.features.pos.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import org.access.managementsystempos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POSAppBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = { Text("Snack Overflow", color = MaterialTheme.colorScheme.onPrimary) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        colors = centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}