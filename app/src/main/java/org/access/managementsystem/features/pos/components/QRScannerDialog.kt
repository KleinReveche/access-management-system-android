package org.access.managementsystem.features.pos.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.access.managementsystem.features.pos.POSScreenViewModel
import org.publicvalue.multiplatform.qrcode.CameraPosition
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions

@Composable
fun QRScannerDialog(vm: POSScreenViewModel) {
    if (vm.showQrScanner) {
        ScannerWithPermissions(
            modifier = Modifier.padding(16.dp),
            onScanned = {
                if (vm.verifyMasterKey(it)) true
                false
            },
            types = listOf(CodeType.QR),
            cameraPosition = CameraPosition.BACK
        )
    }
}