package dz.gov.almizan.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dz.gov.almizan.ui.components.AlMizanButton
import dz.gov.almizan.ui.components.ButtonVariant
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors

private val WILAYAS = listOf(
    "01 - Adrar", "02 - Chlef", "03 - Laghouat", "04 - Oum El Bouaghi", "05 - Batna",
    "06 - Béjaïa", "07 - Biskra", "08 - Béchar", "09 - Blida", "10 - Bouira",
    "11 - Tamanrasset", "12 - Tébessa", "13 - Tlemcen", "14 - Tiaret", "15 - Tizi Ouzou",
    "16 - Alger", "17 - Djelfa", "18 - Jijel", "19 - Sétif", "20 - Saïda",
    "21 - Skikda", "22 - Sidi Bel Abbès", "23 - Annaba", "24 - Guelma", "25 - Constantine",
    "26 - Médéa", "27 - Mostaganem", "28 - M'Sila", "29 - Mascara", "30 - Ouargla",
    "31 - Oran", "32 - El Bayadh", "33 - Illizi", "34 - Bordj Bou Arréridj", "35 - Boumerdès",
    "36 - El Tarf", "37 - Tindouf", "38 - Tissemsilt", "39 - El Oued", "40 - Khenchela",
    "41 - Souk Ahras", "42 - Tipaza", "43 - Mila", "44 - Aïn Defla", "45 - Naâma",
    "46 - Aïn Témouchent", "47 - Ghardaïa", "48 - Relizane",
    "49 - Timimoun", "50 - Bordj Badji Mokhtar", "51 - Ouled Djellal", "52 - Béni Abbès",
    "53 - In Salah", "54 - In Guezzam", "55 - Touggourt", "56 - Djanet",
    "57 - El M'Ghair", "58 - El Meniaa"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var step by remember { mutableIntStateOf(0) } // 0 = identity, 1 = security

    // Step 1 fields
    var prenom by remember { mutableStateOf("") }
    var nom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var organisme by remember { mutableStateOf("") }
    var fonction by remember { mutableStateOf("") }
    var selectedWilaya by remember { mutableStateOf("") }
    var wilayaExpanded by remember { mutableStateOf(false) }

    // Step 2 fields
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val isStep1Valid = prenom.isNotBlank() && nom.isNotBlank() &&
            email.contains("@") && organisme.isNotBlank() && selectedWilaya.isNotBlank()
    val hasLength = password.length >= 8
    val hasUpper = password.any { it.isUpperCase() }
    val hasDigit = password.any { it.isDigit() }
    val passwordsMatch = password == confirmPassword && confirmPassword.isNotEmpty()
    val isStep2Valid = hasLength && hasUpper && hasDigit && passwordsMatch

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
        Column(modifier = Modifier.fillMaxSize()) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (step == 0) onBackClick() else step = 0
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Retour",
                        tint = AlMizanColors.White
                    )
                }
                Text(
                    text = if (step == 0) "Créer un compte" else "Sécurité",
                    style = MaterialTheme.typography.titleMedium,
                    color = AlMizanColors.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Step indicator
            StepIndicator(currentStep = step, totalSteps = 2)

            Spacer(Modifier.height(20.dp))

            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    if (targetState > initialState) {
                        (slideInHorizontally { it } + fadeIn(tween(300))) togetherWith
                                (slideOutHorizontally { -it } + fadeOut(tween(200)))
                    } else {
                        (slideInHorizontally { -it } + fadeIn(tween(300))) togetherWith
                                (slideOutHorizontally { it } + fadeOut(tween(200)))
                    }
                },
                label = "stepContent"
            ) { currentStep ->
                if (currentStep == 0) {
                    Step1Content(
                        prenom = prenom,
                        nom = nom,
                        email = email,
                        organisme = organisme,
                        fonction = fonction,
                        selectedWilaya = selectedWilaya,
                        wilayaExpanded = wilayaExpanded,
                        isValid = isStep1Valid,
                        onPrenomChange = { prenom = it },
                        onNomChange = { nom = it },
                        onEmailChange = { email = it },
                        onOrganismeChange = { organisme = it },
                        onFonctionChange = { fonction = it },
                        onWilayaSelected = { selectedWilaya = it; wilayaExpanded = false },
                        onWilayaExpanded = { wilayaExpanded = it },
                        onNext = { step = 1 }
                    )
                } else {
                    Step2Content(
                        password = password,
                        confirmPassword = confirmPassword,
                        passwordVisible = passwordVisible,
                        confirmVisible = confirmVisible,
                        hasLength = hasLength,
                        hasUpper = hasUpper,
                        hasDigit = hasDigit,
                        passwordsMatch = passwordsMatch,
                        isValid = isStep2Valid,
                        isLoading = isLoading,
                        onPasswordChange = { password = it },
                        onConfirmChange = { confirmPassword = it },
                        onPasswordVisibility = { passwordVisible = !passwordVisible },
                        onConfirmVisibility = { confirmVisible = !confirmVisible },
                        onBack = { step = 0 },
                        onSubmit = {
                            isLoading = true
                            // TODO: real API call
                            onRegisterSuccess()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun StepIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { index ->
            val isActive = index == currentStep
            val isDone = index < currentStep

            Box(
                modifier = Modifier
                    .size(if (isActive) 36.dp else 28.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isDone -> AlMizanColors.SuccessLight
                            isActive -> AlMizanColors.GoldAlMizan
                            else -> AlMizanColors.White.copy(alpha = 0.2f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isDone) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = AlMizanColors.White,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Text(
                        text = "${index + 1}",
                        color = if (isActive) AlMizanColors.NavySovereign else AlMizanColors.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isActive) 14.sp else 12.sp
                    )
                }
            }

            if (index < totalSteps - 1) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .padding(horizontal = 8.dp)
                        .background(
                            if (currentStep > index) AlMizanColors.SuccessLight
                            else AlMizanColors.White.copy(alpha = 0.2f)
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Step1Content(
    prenom: String, nom: String, email: String,
    organisme: String, fonction: String,
    selectedWilaya: String, wilayaExpanded: Boolean, isValid: Boolean,
    onPrenomChange: (String) -> Unit, onNomChange: (String) -> Unit,
    onEmailChange: (String) -> Unit, onOrganismeChange: (String) -> Unit,
    onFonctionChange: (String) -> Unit,
    onWilayaSelected: (String) -> Unit, onWilayaExpanded: (Boolean) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Informations personnelles",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = AlMizanColors.NavySovereign
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Étape 1 sur 2",
                    style = MaterialTheme.typography.bodySmall,
                    color = AlMizanColors.GoldAlMizan
                )
                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    RegisterField(
                        value = prenom, onValueChange = onPrenomChange,
                        label = "Prénom", modifier = Modifier.weight(1f)
                    )
                    RegisterField(
                        value = nom, onValueChange = onNomChange,
                        label = "Nom", modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(12.dp))
                RegisterField(
                    value = email, onValueChange = onEmailChange,
                    label = "Email professionnel",
                    keyboardType = KeyboardType.Email,
                    leadingIcon = { Icon(Icons.Default.Email, null, Modifier.size(18.dp), tint = AlMizanColors.NavySovereign.copy(0.4f)) }
                )
                Spacer(Modifier.height(12.dp))
                RegisterField(
                    value = organisme, onValueChange = onOrganismeChange,
                    label = "Organisme / Institution",
                    leadingIcon = { Icon(Icons.Default.Business, null, Modifier.size(18.dp), tint = AlMizanColors.NavySovereign.copy(0.4f)) }
                )
                Spacer(Modifier.height(12.dp))
                RegisterField(
                    value = fonction, onValueChange = onFonctionChange,
                    label = "Fonction (optionnel)"
                )
                Spacer(Modifier.height(12.dp))

                // Wilaya dropdown
                ExposedDropdownMenuBox(
                    expanded = wilayaExpanded,
                    onExpandedChange = onWilayaExpanded
                ) {
                    OutlinedTextField(
                        value = selectedWilaya,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Wilaya") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = wilayaExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AlMizanColors.GoldAlMizan,
                            unfocusedBorderColor = AlMizanPastelColors.Border,
                            focusedLabelColor = AlMizanColors.GoldAlMizan
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = wilayaExpanded,
                        onDismissRequest = { onWilayaExpanded(false) },
                        modifier = Modifier.heightIn(max = 240.dp)
                    ) {
                        WILAYAS.forEach { wilaya ->
                            DropdownMenuItem(
                                text = { Text(wilaya, style = MaterialTheme.typography.bodySmall) },
                                onClick = { onWilayaSelected(wilaya) }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                AlMizanButton(
                    text = "Continuer →",
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isValid
                )
            }
        }

        Spacer(Modifier.height(40.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Step2Content(
    password: String, confirmPassword: String,
    passwordVisible: Boolean, confirmVisible: Boolean,
    hasLength: Boolean, hasUpper: Boolean, hasDigit: Boolean, passwordsMatch: Boolean,
    isValid: Boolean, isLoading: Boolean,
    onPasswordChange: (String) -> Unit, onConfirmChange: (String) -> Unit,
    onPasswordVisibility: () -> Unit, onConfirmVisibility: () -> Unit,
    onBack: () -> Unit, onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Sécurité du compte",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = AlMizanColors.NavySovereign
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Étape 2 sur 2",
                    style = MaterialTheme.typography.bodySmall,
                    color = AlMizanColors.GoldAlMizan
                )
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Mot de passe") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(Icons.Default.Lock, null, Modifier.size(18.dp), tint = AlMizanColors.NavySovereign.copy(0.4f)) },
                    trailingIcon = {
                        IconButton(onClick = onPasswordVisibility) {
                            Icon(
                                if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null,
                                tint = AlMizanColors.NavySovereign.copy(0.4f)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlMizanColors.GoldAlMizan,
                        unfocusedBorderColor = AlMizanPastelColors.Border,
                        focusedLabelColor = AlMizanColors.GoldAlMizan,
                        cursorColor = AlMizanColors.GoldAlMizan
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(Modifier.height(12.dp))

                // Password requirements
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    PasswordRequirement("Au moins 8 caractères", hasLength)
                    PasswordRequirement("Une lettre majuscule", hasUpper)
                    PasswordRequirement("Un chiffre", hasDigit)
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = onConfirmChange,
                    label = { Text("Confirmer le mot de passe") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(Icons.Default.Lock, null, Modifier.size(18.dp), tint = AlMizanColors.NavySovereign.copy(0.4f)) },
                    trailingIcon = {
                        IconButton(onClick = onConfirmVisibility) {
                            Icon(
                                if (confirmVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null,
                                tint = AlMizanColors.NavySovereign.copy(0.4f)
                            )
                        }
                    },
                    supportingText = if (confirmPassword.isNotEmpty() && !passwordsMatch) {
                        { Text("Les mots de passe ne correspondent pas", color = AlMizanColors.Error) }
                    } else null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (confirmPassword.isNotEmpty() && !passwordsMatch)
                            AlMizanColors.Error else AlMizanColors.GoldAlMizan,
                        unfocusedBorderColor = AlMizanPastelColors.Border,
                        focusedLabelColor = AlMizanColors.GoldAlMizan,
                        cursorColor = AlMizanColors.GoldAlMizan
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(Modifier.height(24.dp))

                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
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
                        text = "Créer mon compte",
                        onClick = onSubmit,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isValid
                    )
                }

                Spacer(Modifier.height(10.dp))

                TextButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ArrowBack, null, Modifier.size(16.dp), tint = AlMizanPastelColors.TextSecondary)
                    Spacer(Modifier.width(4.dp))
                    Text("Retour à l'étape précédente", color = AlMizanPastelColors.TextSecondary)
                }
            }
        }
        Spacer(Modifier.height(40.dp))
    }
}

@Composable
private fun PasswordRequirement(text: String, met: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            if (met) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (met) AlMizanColors.SuccessLight else AlMizanColors.Gray400,
            modifier = Modifier.size(14.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = if (met) AlMizanColors.SuccessLight else AlMizanColors.Gray500
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = if (keyboardType == KeyboardType.Text) KeyboardCapitalization.Words else KeyboardCapitalization.None
        ),
        leadingIcon = leadingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AlMizanColors.GoldAlMizan,
            unfocusedBorderColor = AlMizanPastelColors.Border,
            focusedLabelColor = AlMizanColors.GoldAlMizan,
            cursorColor = AlMizanColors.GoldAlMizan
        ),
        shape = RoundedCornerShape(10.dp)
    )
}
