package org.access.managementsystem.features.pos.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.access.managementsystem.features.pos.POSScreenViewModel

@Composable
fun ProductGrid(vm: POSScreenViewModel) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxHeight()) {
        items(vm.products.filter { vm.selectedCategory == null || it.categoryId == vm.selectedCategory?.id }) { product ->
            ProductItem(product = product, onClick = { vm.addToCart(it) })
        }
    }
}