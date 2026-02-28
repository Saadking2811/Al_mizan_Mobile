package dz.gov.almizan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dz.gov.almizan.R
import dz.gov.almizan.ui.components.AlMizanButton
import dz.gov.almizan.ui.components.ButtonVariant
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            // Logo
            Icon(
                painter = painterResource(id = R.drawable.logo_almizan),
                contentDescription = "Logo Al-Mizan",
                modifier = Modifier.size(100.dp),
                tint = Color.Unspecified
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Title
            Text(
                text = "Al-Mizan",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = AlMizanColors.White
            )
            
            Text(
                text = "الميزان",
                style = MaterialTheme.typography.titleLarge,
                color = AlMizanColors.GoldAlMizan
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Plateforme Nationale des Marchés Publics",
                style = MaterialTheme.typography.bodyMedium,
                color = AlMizanPastelColors.BlueClair,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Login Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Connexion",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = AlMizanColors.NavySovereign
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email ou NIF") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AlMizanColors.GoldAlMizan,
                            focusedLabelColor = AlMizanColors.GoldAlMizan,
                            cursorColor = AlMizanColors.GoldAlMizan
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Mot de passe") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = if (passwordVisible) 
                            VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) 
                                        Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible) 
                                        "Masquer" else "Afficher"
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AlMizanColors.GoldAlMizan,
                            focusedLabelColor = AlMizanColors.GoldAlMizan,
                            cursorColor = AlMizanColors.GoldAlMizan
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Remember me & Forgot password
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = AlMizanColors.GoldAlMizan
                                )
                            )
                            Text(
                                text = "Se souvenir",
                                style = MaterialTheme.typography.bodySmall,
                                color = AlMizanPastelColors.TextSecondary
                            )
                        }
                        
                        TextButton(onClick = onForgotPasswordClick) {
                            Text(
                                text = "Mot de passe oublié ?",
                                style = MaterialTheme.typography.bodySmall,
                                color = AlMizanColors.GoldAlMizan
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Login Button
                    if (isLoading) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = AlMizanColors.GoldAlMizan,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    } else {
                        AlMizanButton(
                            text = "Se connecter",
                            onClick = { isLoading = true; onLoginSuccess() },
                            modifier = Modifier.fillMaxWidth(),
                            variant = ButtonVariant.Primary,
                            enabled = email.isNotBlank() && password.isNotBlank()
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Register link
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Pas encore de compte ? ",
                            style = MaterialTheme.typography.bodySmall,
                            color = AlMizanPastelColors.TextSecondary
                        )
                        TextButton(
                            onClick = onRegisterClick,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "S'inscrire",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = AlMizanColors.GoldAlMizan
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Footer
            Text(
                text = "Conforme à la Loi n° 23-12 et Loi n° 18-07",
                style = MaterialTheme.typography.bodySmall,
                color = AlMizanPastelColors.BlueClair.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}
