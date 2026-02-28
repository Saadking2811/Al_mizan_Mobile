package dz.gov.almizan.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dz.gov.almizan.R
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onLogout: () -> Unit = {}) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var notifyEmail by remember { mutableStateOf(true) }
    var notifyPush by remember { mutableStateOf(true) }
    var notifyDeadlines by remember { mutableStateOf(true) }
    var darkMode by remember { mutableStateOf(false) }
    var arabicLanguage by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val tabs = listOf("Profil", "Notifications", "Sécurité")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AlMizanPastelColors.Background)
    ) {
        // Header with gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(AlMizanColors.NavySovereign, AlMizanColors.BlueRoyal)
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Mon Profil",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = AlMizanColors.White
                    )
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, null, tint = AlMizanColors.White)
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Avatar
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(AlMizanColors.GoldAlMizan.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "AB",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = AlMizanColors.GoldAlMizan
                    )
                    // Edit overlay
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(AlMizanColors.GoldAlMizan)
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            null,
                            tint = AlMizanColors.NavySovereign,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    "Ahmed Benali",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AlMizanColors.White
                )
                Text(
                    "Directeur des marchés publics",
                    style = MaterialTheme.typography.bodySmall,
                    color = AlMizanPastelColors.BlueClair
                )

                Spacer(Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(AlMizanColors.SuccessLight.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            "✓ Vérifié",
                            style = MaterialTheme.typography.labelSmall,
                            color = AlMizanColors.SuccessLight,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(AlMizanColors.GoldAlMizan.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            "Ministère des Finances",
                            style = MaterialTheme.typography.labelSmall,
                            color = AlMizanColors.GoldAlMizan
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Tabs
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = AlMizanColors.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            height = 2.dp,
                            color = AlMizanColors.GoldAlMizan
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    title,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (selectedTab == index)
                                        AlMizanColors.GoldAlMizan else AlMizanColors.White.copy(0.6f)
                                )
                            }
                        )
                    }
                }
            }
        }

        // Tab content
        when (selectedTab) {
            0 -> ProfileTab()
            1 -> NotificationsSettingsTab(
                notifyEmail = notifyEmail,
                notifyPush = notifyPush,
                notifyDeadlines = notifyDeadlines,
                onEmailChanged = { notifyEmail = it },
                onPushChanged = { notifyPush = it },
                onDeadlinesChanged = { notifyDeadlines = it }
            )
            2 -> SecurityTab(
                darkMode = darkMode,
                arabicLanguage = arabicLanguage,
                onDarkModeChanged = { darkMode = it },
                onLanguageChanged = { arabicLanguage = it },
                onLogout = { showLogoutDialog = true }
            )
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Déconnexion", fontWeight = FontWeight.Bold) },
            text = { Text("Êtes-vous sûr de vouloir vous déconnecter de Al-Mizan ?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) {
                    Text("Déconnecter", color = AlMizanColors.Error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Annuler")
                }
            }
        )
    }
}

@Composable
private fun ProfileTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Stats row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileStatCard("23", "AO consultés", Icons.Default.Visibility, AlMizanColors.Info, Modifier.weight(1f))
            ProfileStatCard("7", "AO favoris", Icons.Default.Bookmark, AlMizanColors.GoldAlMizan, Modifier.weight(1f))
            ProfileStatCard("3", "Soumissions", Icons.Default.Send, AlMizanColors.SuccessLight, Modifier.weight(1f))
        }

        Spacer(Modifier.height(4.dp))

        ProfileSectionCard("Informations personnelles") {
            ProfileInfoRow(Icons.Default.Person, "Nom complet", "Ahmed Benali")
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            ProfileInfoRow(Icons.Default.Email, "Email", "a.benali@finances.dz")
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            ProfileInfoRow(Icons.Default.Phone, "Téléphone", "+213 21 XX XX XX")
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            ProfileInfoRow(Icons.Default.LocationOn, "Wilaya", "16 - Alger")
        }

        ProfileSectionCard("Informations professionnelles") {
            ProfileInfoRow(Icons.Default.Business, "Organisme", "Ministère des Finances")
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            ProfileInfoRow(Icons.Default.Work, "Fonction", "Directeur des marchés publics")
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            ProfileInfoRow(Icons.Default.Numbers, "NIF", "00123456789")
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            ProfileInfoRow(Icons.Default.CalendarToday, "Membre depuis", "Janvier 2024")
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun NotificationsSettingsTab(
    notifyEmail: Boolean, notifyPush: Boolean, notifyDeadlines: Boolean,
    onEmailChanged: (Boolean) -> Unit, onPushChanged: (Boolean) -> Unit,
    onDeadlinesChanged: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileSectionCard("Canal de notification") {
            SettingsSwitchRow(
                icon = Icons.Default.Email,
                title = "Notifications par email",
                subtitle = "Recevoir des alertes par email",
                checked = notifyEmail,
                onCheckedChange = onEmailChanged
            )
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            SettingsSwitchRow(
                icon = Icons.Default.Notifications,
                title = "Notifications push",
                subtitle = "Alertes en temps réel sur l'appareil",
                checked = notifyPush,
                onCheckedChange = onPushChanged
            )
        }
        ProfileSectionCard("Types d'alertes") {
            SettingsSwitchRow(
                icon = Icons.Default.Schedule,
                title = "Rappels de délais",
                subtitle = "48h avant expiration des AO",
                checked = notifyDeadlines,
                onCheckedChange = onDeadlinesChanged
            )
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            SettingsSwitchRow(
                icon = Icons.Default.Description,
                title = "Nouveaux appels d'offres",
                subtitle = "Selon vos critères de filtrage",
                checked = true,
                onCheckedChange = {}
            )
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            SettingsSwitchRow(
                icon = Icons.Default.EmojiEvents,
                title = "Résultats d'attribution",
                subtitle = "Résultats des marchés soumis",
                checked = true,
                onCheckedChange = {}
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun SecurityTab(
    darkMode: Boolean, arabicLanguage: Boolean,
    onDarkModeChanged: (Boolean) -> Unit, onLanguageChanged: (Boolean) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileSectionCard("Sécurité") {
            SettingsActionRow(
                icon = Icons.Default.Lock,
                title = "Changer le mot de passe",
                subtitle = "Dernière modification il y a 60 jours",
                onClick = {}
            )
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            SettingsActionRow(
                icon = Icons.Default.Security,
                title = "Authentification à deux facteurs",
                subtitle = "Non activée",
                onClick = {}
            )
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            SettingsActionRow(
                icon = Icons.Default.DevicesOther,
                title = "Appareils connectés",
                subtitle = "2 appareils actifs",
                onClick = {}
            )
        }

        ProfileSectionCard("Préférences") {
            SettingsSwitchRow(
                icon = Icons.Default.DarkMode,
                title = "Mode sombre",
                subtitle = "Thème sombre de l'application",
                checked = darkMode,
                onCheckedChange = onDarkModeChanged
            )
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            SettingsSwitchRow(
                icon = Icons.Default.Language,
                title = "عربي",
                subtitle = "Affichage en langue arabe",
                checked = arabicLanguage,
                onCheckedChange = onLanguageChanged
            )
        }

        ProfileSectionCard("Informations légales") {
            SettingsActionRow(
                icon = Icons.Default.Gavel,
                title = "Conditions d'utilisation",
                subtitle = "Loi n° 23-12 relative aux marchés publics",
                onClick = {}
            )
            HorizontalDivider(color = AlMizanPastelColors.Border.copy(0.5f))
            SettingsActionRow(
                icon = Icons.Default.PrivacyTip,
                title = "Politique de confidentialité",
                subtitle = "Loi n° 18-07 sur la protection des données",
                onClick = {}
            )
        }

        // Logout
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLogout() },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = AlMizanColors.Error.copy(alpha = 0.06f)
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(AlMizanColors.Error.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Logout, null, tint = AlMizanColors.Error, modifier = Modifier.size(20.dp))
                }
                Spacer(Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Se déconnecter",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AlMizanColors.Error
                    )
                    Text(
                        "Quitter la session en cours",
                        style = MaterialTheme.typography.bodySmall,
                        color = AlMizanColors.Error.copy(0.6f)
                    )
                }
                Icon(Icons.Default.ChevronRight, null, tint = AlMizanColors.Error.copy(0.5f))
            }
        }

        Spacer(Modifier.height(24.dp))

        // App version
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Al-Mizan v1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = AlMizanColors.Gray400
                )
                Text(
                    "© 2025 Ministère des Finances - DGB",
                    style = MaterialTheme.typography.labelSmall,
                    color = AlMizanColors.Gray300
                )
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

// ── Shared sub-components ─────────────────────────────────

@Composable
private fun ProfileSectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(visible = visible, enter = fadeIn() + expandVertically()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = AlMizanColors.NavySovereign,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                HorizontalDivider(color = AlMizanColors.GoldAlMizan.copy(0.3f), thickness = 1.dp)
                content()
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = AlMizanColors.NavySovereign.copy(0.45f), modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = AlMizanColors.Gray400)
            Text(value, style = MaterialTheme.typography.bodyMedium, color = AlMizanColors.NavySovereign, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(AlMizanColors.NavySovereign.copy(0.06f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = AlMizanColors.NavySovereign.copy(0.6f), modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = AlMizanColors.NavySovereign)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = AlMizanColors.Gray400)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = AlMizanColors.White,
                checkedTrackColor = AlMizanColors.GoldAlMizan,
                uncheckedTrackColor = AlMizanColors.Gray200
            )
        )
    }
}

@Composable
private fun SettingsActionRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(AlMizanColors.NavySovereign.copy(0.06f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = AlMizanColors.NavySovereign.copy(0.6f), modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = AlMizanColors.NavySovereign)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = AlMizanColors.Gray400)
        }
        Icon(Icons.Default.ChevronRight, null, tint = AlMizanColors.Gray300, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun ProfileStatCard(
    value: String,
    label: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(22.dp))
            Spacer(Modifier.height(6.dp))
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = color)
            Text(label, style = MaterialTheme.typography.labelSmall, color = AlMizanColors.Gray400, maxLines = 1)
        }
    }
}

private fun Modifier.tabIndicatorOffset(tabPosition: TabPosition): Modifier =
    this.fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = tabPosition.left)
        .width(tabPosition.width)
