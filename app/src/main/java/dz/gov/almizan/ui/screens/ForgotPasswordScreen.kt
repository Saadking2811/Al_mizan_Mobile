package dz.gov.almizan.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dz.gov.almizan.ui.components.AlMizanButton
import dz.gov.almizan.ui.components.ButtonVariant
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(1500)
            isLoading = false
            isSuccess = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        AlMizanColors.NavySovereign,
                        AlMizanColors.BlueRoyal,
                        AlMizanColors.BlueSky
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(56.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Retour",
                        tint = AlMizanColors.White
                    )
                }
                Text(
                    text = "Récupération",
                    style = MaterialTheme.typography.titleMedium,
                    color = AlMizanColors.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(40.dp))

            AnimatedContent(
                targetState = isSuccess,
                transitionSpec = {
                    (fadeIn(tween(400)) + slideInVertically { it / 4 }) togetherWith
                            fadeOut(tween(200))
                },
                label = "successContent"
            ) { success ->
                if (success) {
                    SuccessContent(
                        email = email,
                        onBack = onBackClick
                    )
                } else {
                    FormContent(
                        email = email,
                        isLoading = isLoading,
                        onEmailChange = { email = it },
                        onSubmit = { isLoading = true }
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun FormContent(
    email: String,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Icon
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(AlMizanColors.GoldAlMizan.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Lock,
                contentDescription = null,
                tint = AlMizanColors.GoldAlMizan,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Mot de passe oublié ?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AlMizanColors.White,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Saisissez votre adresse email pour recevoir\nun lien de réinitialisation.",
            style = MaterialTheme.typography.bodyMedium,
            color = AlMizanPastelColors.BlueClair,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(36.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Adresse email",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = AlMizanColors.NavySovereign
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = { Text("exemple@organisme.dz") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,
                            tint = AlMizanColors.NavySovereign.copy(alpha = 0.5f)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlMizanColors.GoldAlMizan,
                        unfocusedBorderColor = AlMizanPastelColors.Border,
                        focusedLabelColor = AlMizanColors.GoldAlMizan,
                        cursorColor = AlMizanColors.GoldAlMizan
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(20.dp))

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = AlMizanColors.GoldAlMizan,
                            strokeWidth = 2.5.dp,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                } else {
                    AlMizanButton(
                        text = "Envoyer le lien",
                        onClick = onSubmit,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = email.contains("@") && email.contains(".")
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        TextButton(onClick = {}) {
            Text(
                text = "Vous n'avez pas reçu l'email ?",
                style = MaterialTheme.typography.bodySmall,
                color = AlMizanPastelColors.BlueClair.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun SuccessContent(email: String, onBack: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(AlMizanColors.SuccessLight.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = AlMizanColors.SuccessLight,
                modifier = Modifier.size(58.dp)
            )
        }

        Spacer(Modifier.height(28.dp))

        Text(
            text = "Email envoyé !",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AlMizanColors.White
        )

        Spacer(Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = AlMizanColors.White.copy(alpha = 0.08f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Un lien de réinitialisation a été envoyé à",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AlMizanPastelColors.BlueClair,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = AlMizanColors.GoldAlMizan,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Vérifiez votre boîte de réception et suivez les instructions.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AlMizanPastelColors.BlueClair.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        AlMizanButton(
            text = "Retour à la connexion",
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Primary
        )

        Spacer(Modifier.height(12.dp))

        TextButton(onClick = {}) {
            Text(
                text = "Renvoyer l'email",
                style = MaterialTheme.typography.bodySmall,
                color = AlMizanPastelColors.BlueClair.copy(alpha = 0.7f)
            )
        }
    }
}
