package org.access.managementsystempos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.access.managementsystempos.features.navigation.Navigation
import org.access.managementsystempos.ui.theme.ACCESSManagementSystemPOSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ACCESSManagementSystemPOSTheme {
                Navigation()
            }
        }
    }
}