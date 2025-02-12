package org.access.managementsystempos.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.R
import org.access.managementsystempos.features.common.icons.Logout
import org.access.managementsystempos.features.kitchen.KitchenScreenDestination
import org.access.managementsystempos.features.login.LoginScreenDestination
import org.access.managementsystempos.features.main.components.ElevatedCardButton
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.POSScreenDestination
import org.access.managementsystempos.features.sales.SalesScreenDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(navController: NavController) {
    val vm: MainScreenViewModel = koinViewModel()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    bitmap = vm.avatar.asImageBitmap(),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .width(128.dp)
                        .height(128.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )

                Text(
                    vm.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(vm.role, style = MaterialTheme.typography.bodyMedium)
                Text(
                    "Login time: ${vm.getLoginTimeFormatted()}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                AssistChip(
                    onClick = {
                        vm.onLogout {
                            if (!navController.popBackStack()) {
                                navController.navigate(LoginScreenDestination)
                            }
                        }
                    },
                    label = { Text("Logout") },
                    leadingIcon = {
                        Icon(
                            imageVector = Logout,
                            contentDescription = "Logout",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ElevatedCardButton(
                    text = stringResource(R.string.point_of_sale),
                    iconRes = R.drawable.ic_pos,
                    enabled = vm.role == "admin" || vm.role == "cashier"
                ) {
                    navController.navigate(POSScreenDestination)
                }

                ElevatedCardButton(
                    text = "Kitchen",
                    iconRes = R.drawable.ic_kitchen,
                ) {
                    navController.navigate(KitchenScreenDestination)
                }

                ElevatedCardButton(
                    text = "Sales",
                    iconRes = R.drawable.ic_sales,
                )
                {
                    navController.navigate(SalesScreenDestination)
                }
            }
        }
    }
}

@Serializable
object MainScreenDestination : ScreenDestination

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(NavController(LocalContext.current))
}
