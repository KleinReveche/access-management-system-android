package org.access.managementsystem.features.common.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.access.managementsystem.R

@Preview
@Composable
fun PreviewArcRotationAnimation() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ArcRotationAnimation()
    }
}

@Composable
fun ArcRotationAnimation() {
    val infiniteTransition = rememberInfiniteTransition()


    Color(0xFFAFE1AF)
    val arcColor = Color(0XFFFFFFFF)
    val arcAngle1 by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 180F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val arcAngle2 by infiniteTransition.animateFloat(
        initialValue = 180F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val logo = painterResource(R.drawable.ic_launcher_foreground) as ImageBitmap

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .padding(12.dp)
                .size(100.dp)
        ) {
            drawArc(
                color = arcColor,
                startAngle = arcAngle1,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = 10f, cap = StrokeCap.Round),
            )

            drawArc(
                color = arcColor,
                startAngle = arcAngle2,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = 10f, cap = StrokeCap.Round),
            )

            drawCircle(
                color = arcColor,
                radius = 120f,
            )

            drawImage(
                image = logo,
            )
        }
    }
}