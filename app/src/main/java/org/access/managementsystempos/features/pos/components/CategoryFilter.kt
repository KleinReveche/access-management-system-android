package org.access.managementsystempos.features.pos.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.access.managementsystempos.R
import org.access.managementsystempos.features.pos.POSScreenViewModel

@Composable
fun CategoryFilter(vm: POSScreenViewModel) {
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
}