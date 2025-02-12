package org.access.managementsystempos.features.pos.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.access.managementsystempos.BuildConfig
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
                AsyncImage(
                    model = BuildConfig.BASE_URL + "main/uploads/" + product.image,
                    contentDescription = null,
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
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
