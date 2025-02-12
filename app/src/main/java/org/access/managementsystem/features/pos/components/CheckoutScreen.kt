package org.access.managementsystem.features.pos.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable
import org.access.managementsystem.BuildConfig
import org.access.managementsystem.R
import org.access.managementsystem.domain.models.Order
import org.access.managementsystem.domain.models.Product
import org.access.managementsystem.features.navigation.ScreenDestination
import org.access.managementsystem.features.pos.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    totalPrice: Float,
    sharedViewModel: SharedViewModel
) {
    val cart = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Map<Product, Int>>("cart") ?: emptyMap()

    var showCashSheet by remember { mutableStateOf(false) }
    var showCashlessSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(cart.entries.toList()) { (product, quantity) ->
                    CheckoutItemRow(product, quantity)
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", style = MaterialTheme.typography.titleLarge)
                Text("₱${"%.2f".format(totalPrice)}", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Select Payment Method", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showCashSheet = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cash")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showCashlessSheet = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cashless (Online Payment)")
            }
        }
    }

    if (showCashSheet) {
        CashPaymentBottomSheet(
            totalPrice = totalPrice,
            onDismiss = { showCashSheet = false },
            onConfirm = { customerName, gradeProgram, cashPaid ->
                val newOrder = Order(
                    customerName = customerName,
                    gradeProgram = gradeProgram,
                    items = cart,
                    paymentType = "Cash"
                )

                sharedViewModel.addOrder(newOrder)
                showCashSheet = false
                navController.popBackStack()
            }
        )
    }

    if (showCashlessSheet) {
        CashlessPaymentBottomSheet(
            onDismiss = { showCashlessSheet = false },
            onConfirm = { customerName, gradeProgram, paymentProof ->
                val newOrder = Order(
                    customerName = customerName,
                    gradeProgram = gradeProgram,
                    items = cart,
                    paymentType = "Cashless",
                    paymentProof = paymentProof
                )

                sharedViewModel.addOrder(newOrder)
                showCashlessSheet = false
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun CheckoutItemRow(product: Product, quantity: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = BuildConfig.BASE_URL + "main/uploads/" + product.image,
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(product.name, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "₱${"%.2f".format(product.price)} x $quantity",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        Text(
            text = "₱${"%.2f".format(product.price * quantity)}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashPaymentBottomSheet(
    totalPrice: Float,
    onDismiss: () -> Unit,
    onConfirm: (customerName: String, gradeProgram: String, cashPaid: Float) -> Unit
) {
    var customerName by remember { mutableStateOf("") }
    var gradeProgram by remember { mutableStateOf("") }
    var cashPaid by remember { mutableStateOf("") }

    val changeAmount = remember(cashPaid) {
        cashPaid.toFloatOrNull()?.minus(totalPrice) ?: 0f
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Cash Payment", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = customerName,
                onValueChange = { customerName = it },
                label = { Text("Customer's Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = gradeProgram,
                onValueChange = { gradeProgram = it },
                label = { Text("Grade / Program") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = cashPaid,
                onValueChange = { cashPaid = it },
                label = { Text("Cash Paid (₱)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = "₱${"%.2f".format(changeAmount)}",
                onValueChange = {},
                label = { Text("Change Amount") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Button(onClick = {
                    onConfirm(customerName, gradeProgram, cashPaid.toFloatOrNull() ?: 0f)
                }) { Text("Confirm Order") }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashlessPaymentBottomSheet(
    onDismiss: () -> Unit,
    onConfirm: (customerName: String, gradeProgram: String, paymentProof: String) -> Unit
) {
    var customerName by remember { mutableStateOf("") }
    var gradeProgram by remember { mutableStateOf("") }
    var paymentProof by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Cashless Payment", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = customerName,
                onValueChange = { customerName = it },
                label = { Text("Customer's Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = gradeProgram,
                onValueChange = { gradeProgram = it },
                label = { Text("Grade / Program") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = paymentProof,
                onValueChange = { paymentProof = it },
                label = { Text("Payment Proof") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Button(onClick = {
                    onConfirm(
                        customerName,
                        gradeProgram,
                        paymentProof
                    )
                }) { Text("Confirm Order") }
            }
        }
    }
}

@Serializable
object CheckoutScreenDestination : ScreenDestination {
    fun createRoute(totalPrice: Float) = "checkout_screen/$totalPrice"
}