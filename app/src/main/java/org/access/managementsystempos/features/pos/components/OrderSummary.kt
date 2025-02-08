package org.access.managementsystempos.features.pos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.access.managementsystempos.domain.models.Product

@Composable
fun OrderSummary(cart: Map<Product, Int>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        cart.forEach { (product, quantity) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val imageId = product.imageId ?: android.R.drawable.ic_menu_gallery
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = product.name,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "${product.name}- ₱${product.price} x $quantity - ${
                        product.price.times(quantity)
                    } PHP"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
