package dz.gov.almizan.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dz.gov.almizan.R
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    var showLoader by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "alpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.65f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    val loaderAlpha by animateFloatAsState(
        targetValue = if (showLoader) 1f else 0f,
        animationSpec = tween(600),
        label = "loaderAlpha"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(800)
        showLoader = true
        delay(1800)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        AlMizanColors.BlueRoyal,
                        AlMizanColors.NavySovereign,
                        AlMizanColors.NavyDeep
                    ),
                    radius = 1400f
                )
            )
    ) {
        // Decorative circles
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset((-80).dp, (-80).dp)
                .clip(CircleShape)
                .background(AlMizanColors.GoldAlMizan.copy(alpha = 0.04f))
                .align(Alignment.TopStart)
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(60.dp, 40.dp)
                .clip(CircleShape)
                .background(AlMizanColors.BlueSky.copy(alpha = 0.06f))
                .align(Alignment.BottomEnd)
        )

        // Center content
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(alpha)
                .scale(scale),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo circle
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .background(AlMizanColors.White.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_almizan),
                    contentDescription = "Logo Al-Mizan",
                    modifier = Modifier.size(88.dp)
                )
            }

            Spacer(Modifier.height(28.dp))

            Text(
                text = "Al-Mizan",
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AlMizanColors.White,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "الميزان",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = AlMizanColors.GoldAlMizan
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Plateforme Nationale des Marchés Publics",
                style = MaterialTheme.typography.bodyMedium,
                color = AlMizanPastelColors.BlueClair,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(Modifier.height(6.dp))

            // Gold divider
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                AlMizanColors.GoldAlMizan.copy(alpha = 0f),
                                AlMizanColors.GoldAlMizan,
                                AlMizanColors.GoldAlMizan.copy(alpha = 0f)
                            )
                        )
                    )
            )
        }

        // Bottom area
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 56.dp)
                .alpha(loaderAlpha),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = AlMizanColors.GoldAlMizan,
                strokeWidth = 2.dp,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Ministère des Finances · DGB",
                style = MaterialTheme.typography.bodySmall,
                color = AlMizanPastelColors.BlueClair.copy(alpha = 0.55f),
                letterSpacing = 0.3.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "v1.0.0",
                style = MaterialTheme.typography.labelSmall,
                color = AlMizanColors.GoldAlMizan.copy(alpha = 0.4f)
            )
        }
    }
}
