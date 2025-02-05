package org.access.managementsystempos.features.pos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.access.managementsystempos.data.SampleData
import org.access.managementsystempos.data.SampleData.categories
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.components.OrderSummary
import org.access.managementsystempos.features.pos.components.ProductItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun POSScreen() {
    val scaffoldState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val vm: POSScreenViewModel = viewModel { POSScreenViewModel() }
    var showBottomSheet by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = scaffoldState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text("Categories", style = MaterialTheme.typography.headlineMedium)
                categories.forEachIndexed { index, category ->
                    TextButton(onClick = {
                        vm.selectedCategory = index
                        scope.launch { scaffoldState.close() }
                    }) {
                        Text(category.name)
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Point of Sale") }, navigationIcon = {
                    IconButton(onClick = { scope.launch { scaffoldState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                })
            },
            bottomBar = {
                Button(
                    onClick = { showBottomSheet = true },
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
                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
                    items(SampleData.food) { product ->
                        ProductItem(product) { vm.addToCart(it) }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            OrderSummary(vm.cart)
        }
    }
}

@Serializable
object POSScreenDestination : ScreenDestination