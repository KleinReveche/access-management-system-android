package org.access.managementsystempos.features.pos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.R
import org.access.managementsystempos.data.SampleData
import org.access.managementsystempos.domain.models.Order
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.components.OrderSummary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POSScreen(sharedViewModel: SharedViewModel, navController: NavController) {
    val vm: POSScreenViewModel = viewModel()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(SampleData.categories[0]) }
    var showClearDialog by remember { mutableStateOf(false) }

    val productsToShow = SampleData.food.filter { product ->
        selectedCategory == SampleData.categories[0] || product.productCategory == selectedCategory
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Access™ Menu", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            Button(
                onClick = { showBottomSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Order List")
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
                items(SampleData.categories) { category ->
                    FilterChip(
                        selected = category == selectedCategory,
                        onClick = { selectedCategory = category },
                        label = { Text(category.name) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
                items(productsToShow) { product ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            product.imageId?.let {
                                Image(
                                    painter = painterResource(id = it),
                                    contentDescription = product.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                )
                            } ?: run {
                                Image(
                                    painter = painterResource(id = R.drawable.placeholder_image),
                                    contentDescription = "Placeholder",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "₱${product.price}", style = MaterialTheme.typography.bodySmall)
                        Button(
                            onClick = { vm.addToCart(product) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text("Add to List")
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
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
                        onClick = { showClearDialog = true },
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
                            val order = Order(
                                id = "ORD-${System.currentTimeMillis()}",
                                items = vm.cart.toMap(),
                                elapsedTime = 0
                            )
                            sharedViewModel.addOrder(order)
                            vm.clearCart()
                            showBottomSheet = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Send to Kitchen")
                    }
                }
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Clear Order") },
            text = { Text("Are you sure you want to clear the order?") },
            confirmButton = {
                IconButton(
                    onClick = {
                        vm.clearCart()
                        showClearDialog = false
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
                    onClick = { showClearDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
@Serializable
object POSScreenDestination : ScreenDestination