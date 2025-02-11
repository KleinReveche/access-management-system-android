package org.access.managementsystempos.features.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.access.managementsystempos.R

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        SettingsOption(
            title = "Account",
            description = "Manage your account details",
            iconRes = R.drawable.ic_account,
            onClick = { } //Help, I dunno how edit acc info :(
        )

        SettingsOption(
            title = "Help & Support",
            description = "Get assistance or send feedback",
            iconRes = R.drawable.ic_support,
            onClick = { } //Help, diko alam kung ano email na ididirect dito.
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_language),
                contentDescription = "Language",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("Language", color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.weight(1f))
            Text("English", color = MaterialTheme.colorScheme.primary)
        }

        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        AboutSection()
    }
}

@Composable
fun SettingsOption(title: String, description: String, iconRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, color = MaterialTheme.colorScheme.onSurface)
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AboutSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Access™ POS",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Version 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}