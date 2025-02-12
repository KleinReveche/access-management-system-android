package org.access.managementsystempos.features.pos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.R
import org.access.managementsystempos.domain.models.Order
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.components.OrderSummary
import org.access.managementsystempos.features.pos.components.ProductItem
import org.koin.androidx.compose.koinViewModel
import org.publicvalue.multiplatform.qrcode.CameraPosition
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POSScreen(navController: NavController) {
    val vm: POSScreenViewModel = koinViewModel()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Snack Overflow",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            Button(
                onClick = { vm.showBottomSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Checkout")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyRow {
                item {
                    FilterChip(
                        selected = vm.selectedCategory == null,
                        onClick = { vm.selectedCategory = null },
                        label = { Text(stringResource(R.string.all)) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
                items(vm.productCategories) { category ->
                    FilterChip(
                        selected = category == vm.selectedCategory,
                        onClick = { vm.selectedCategory = category },
                        label = { Text(category.name) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
                items(
                    vm.products.filter { vm.selectedCategory == null || it.categoryId == vm.selectedCategory?.id }
                ) { product ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        ProductItem(product) { vm.addToCart(product) }
                    }
                }
            }
        }
    }

    if (vm.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { vm.showBottomSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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

                Spacer(modifier = Modifier.height(16.dp))

                OrderSummary(vm.cart)

                Spacer(modifier = Modifier.height(16.dp))

                if (vm.cart.isNotEmpty()) {
                    Button(
                        onClick = {
                            Order(
                                id = "ORD-${System.currentTimeMillis()}",
                                items = vm.cart.toMap(),
                                elapsedTime = 0
                            )
                            vm.clearCart()
                            vm.showBottomSheet = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Send to Kitchen")
                    }
                }
            }
        }
    }

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

    if (vm.showQrScanner) {
        ScannerWithPermissions(
            modifier = Modifier.padding(16.dp),
            onScanned = {
                if (vm.verifyMasterKey(it)) true
                false
            },
            types = listOf(CodeType.QR),
            cameraPosition = CameraPosition.BACK
        )
    }
}
@Serializable
object POSScreenDestination : ScreenDestination