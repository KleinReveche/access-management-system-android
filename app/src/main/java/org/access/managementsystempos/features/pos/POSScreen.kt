package org.access.managementsystempos.features.pos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.serialization.Serializable
import org.access.managementsystempos.data.SampleData
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.components.OrderSummary
import org.access.managementsystempos.features.pos.components.ProductItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun POSScreen() {
    val vm: POSScreenViewModel = viewModel { POSScreenViewModel() }
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(SampleData.categories[0]) }

    val productsToShow = SampleData.food.filter { product ->
        selectedCategory == SampleData.categories[0] || product.productCategory == selectedCategory
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Point of Sale") })
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
                    ProductItem(product) { vm.addToCart(it) }
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