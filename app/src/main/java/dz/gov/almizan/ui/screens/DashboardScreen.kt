package dz.gov.almizan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dz.gov.almizan.ui.components.StatusBadge
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onBackClick: () -> Unit,
    onTenderClick: (String) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                AlMizanColors.NavySovereign,
                                AlMizanColors.BlueRoyal
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
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
                            text = "Tableau de bord",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = AlMizanColors.White
                        )
                        
                        IconButton(onClick = { /* Settings */ }) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "Paramètres",
                                tint = AlMizanColors.White
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // User Profile
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(AlMizanColors.GoldAlMizan.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "AB",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = AlMizanColors.GoldAlMizan
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column {
                            Text(
                                text = "Ahmed Benali",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = AlMizanColors.White
                            )
                            Text(
                                text = "Entreprise BTP Excellence",
                                style = MaterialTheme.typography.bodyMedium,
                                color = AlMizanPastelColors.BlueClair
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AlMizanPastelColors.Background)
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Statistics Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardStatCard(
                    value = "12",
                    label = "Soumissions",
                    icon = "📝",
                    accentColor = AlMizanColors.GoldAlMizan,
                    modifier = Modifier.weight(1f)
                )
                
                DashboardStatCard(
                    value = "5",
                    label = "En cours",
                    icon = "⏳",
                    accentColor = AlMizanPastelColors.BluePrincipal,
                    modifier = Modifier.weight(1f)
                )
                
                DashboardStatCard(
                    value = "3",
                    label = "Acceptées",
                    icon = "✓",
                    accentColor = AlMizanColors.Success,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = AlMizanColors.White,
                contentColor = AlMizanColors.NavySovereign,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .width(tabPositions[selectedTab].width)
                            .offset(x = tabPositions[selectedTab].left),
                        color = AlMizanColors.GoldAlMizan,
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Mes soumissions") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Favoris") }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Notifications") }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            when (selectedTab) {
                0 -> {
                    // My Submissions
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(mySubmissions) { submission ->
                            SubmissionCard(
                                submission = submission,
                                onClick = { onTenderClick(submission.tenderId) }
                            )
                        }
                    }
                }
                1 -> {
                    // Favorites
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(favoritesTenders) { tender ->
                            FavoriteCard(
                                tender = tender,
                                onClick = { onTenderClick(tender.id) }
                            )
                        }
                    }
                }
                2 -> {
                    // Notifications
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(notifications) { notification ->
                            NotificationCard(notification = notification)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardStatCard(
    value: String,
    label: String,
    icon: String,
    accentColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = AlMizanColors.NavySovereign
            )
            
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = AlMizanPastelColors.TextSecondary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubmissionCard(
    submission: Submission,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        when (submission.status) {
                            "EN_COURS" -> AlMizanPastelColors.WarningPastel
                            "ACCEPTÉE" -> AlMizanPastelColors.SuccessPastel
                            "REJETÉE" -> AlMizanPastelColors.ErrorPastel
                            else -> AlMizanPastelColors.InfoPastel
                        }
                    )
            )
            
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = submission.reference,
                            style = MaterialTheme.typography.labelMedium,
                            color = AlMizanColors.GoldAlMizan,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = submission.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AlMizanColors.NavySovereign
                        )
                    }
                    
                    StatusBadge(status = submission.status)
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Soumis le ${submission.submittedDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = AlMizanPastelColors.TextTertiary
                    )
                    Text(
                        text = submission.amount,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AlMizanPastelColors.TextPrimary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoriteCard(
    tender: FavoriteTender,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = AlMizanColors.GoldAlMizan
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tender.reference,
                    style = MaterialTheme.typography.labelSmall,
                    color = AlMizanPastelColors.TextSecondary
                )
                Text(
                    text = tender.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = AlMizanPastelColors.TextPrimary
                )
            }
            
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = AlMizanPastelColors.TextTertiary
            )
        }
    }
}

@Composable
private fun NotificationCard(
    notification: Notification
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) AlMizanColors.White 
                           else AlMizanPastelColors.BlueClair.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(notification.iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    notification.icon,
                    contentDescription = null,
                    tint = notification.iconColor
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AlMizanPastelColors.TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = AlMizanPastelColors.TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = AlMizanPastelColors.TextTertiary
                )
            }
        }
    }
}

// Sample Data
data class Submission(
    val tenderId: String,
    val reference: String,
    val title: String,
    val status: String,
    val submittedDate: String,
    val amount: String
)

private val mySubmissions = listOf(
    Submission("1", "AO-2026-001", "Construction lycée Alger", "EN_COURS", "10 Fév 2026", "425M DZD"),
    Submission("2", "AO-2026-002", "Matériel informatique", "ACCEPTÉE", "05 Fév 2026", "82M DZD"),
    Submission("3", "AO-2026-003", "Rénovation routes Oran", "EN_COURS", "01 Fév 2026", "315M DZD")
)

data class FavoriteTender(
    val id: String,
    val reference: String,
    val title: String
)

private val favoritesTenders = listOf(
    FavoriteTender("4", "AO-2026-004", "Services maintenance hospitalière"),
    FavoriteTender("5", "AO-2026-005", "Étude projet autoroutier"),
    FavoriteTender("6", "AO-2026-006", "Fourniture équipements médicaux")
)

data class Notification(
    val title: String,
    val message: String,
    val time: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: androidx.compose.ui.graphics.Color,
    val isRead: Boolean
)

private val notifications = listOf(
    Notification(
        "Soumission acceptée",
        "Votre offre pour AO-2026-002 a été acceptée",
        "Il y a 2 heures",
        Icons.Default.CheckCircle,
        AlMizanColors.Success,
        false
    ),
    Notification(
        "Nouveau document",
        "Un addendum a été ajouté à AO-2026-001",
        "Il y a 1 jour",
        Icons.Default.Description,
        AlMizanPastelColors.BluePrincipal,
        false
    ),
    Notification(
        "Rappel échéance",
        "Date limite AO-2026-003 dans 3 jours",
        "Il y a 2 jours",
        Icons.Default.Schedule,
        AlMizanColors.Warning,
        true
    )
)
