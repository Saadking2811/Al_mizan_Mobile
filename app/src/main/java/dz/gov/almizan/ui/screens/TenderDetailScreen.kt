package dz.gov.almizan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.dp
import dz.gov.almizan.ui.components.AlMizanButton
import dz.gov.almizan.ui.components.ButtonVariant
import dz.gov.almizan.ui.components.StatusBadge
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenderDetailScreen(
    tenderId: String,
    onBackClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails de l'appel d'offres") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Partager")
                    }
                    IconButton(onClick = { /* Favorite */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favori")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AlMizanColors.NavySovereign,
                    titleContentColor = AlMizanColors.White,
                    navigationIconContentColor = AlMizanColors.White,
                    actionIconContentColor = AlMizanColors.GoldAlMizan
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AlMizanPastelColors.Background)
                .padding(paddingValues)
        ) {
            item {
                // Header Card
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
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "AO-2026-001",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = AlMizanColors.GoldAlMizan,
                                    fontWeight = FontWeight.SemiBold
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Text(
                                    text = "Construction d'un lycée moderne à Alger",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = AlMizanColors.White
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            StatusBadge(status = "OUVERT")
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            InfoColumn(
                                label = "Budget",
                                value = "450M DZD",
                                icon = "💰"
                            )
                            
                            InfoColumn(
                                label = "Échéance",
                                value = "15 Mars 2026",
                                icon = "📅"
                            )
                            
                            InfoColumn(
                                label = "Secteur",
                                value = "Travaux",
                                icon = "🏗️"
                            )
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
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
                        text = { Text("Détails") }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Documents") }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Contact") }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            when (selectedTab) {
                0 -> {
                    item {
                        // Details Content
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = AlMizanColors.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                SectionTitle("Description")
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Text(
                                    text = "Construction d'un établissement scolaire moderne avec une capacité de 800 élèves, incluant salles de classe, laboratoires, bibliothèque, terrain de sport et espaces administratifs.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = AlMizanPastelColors.TextPrimary
                                )
                                
                                Spacer(modifier = Modifier.height(20.dp))
                                
                                SectionTitle("Spécifications techniques")
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                SpecificationItem("Surface totale", "12,000 m²")
                                SpecificationItem("Nombre de salles", "32 salles de classe")
                                SpecificationItem("Laboratoires", "4 laboratoires scientifiques")
                                SpecificationItem("Infrastructures", "Gymnase, bibliothèque, cantine")
                                
                                Spacer(modifier = Modifier.height(20.dp))
                                
                                SectionTitle("Conditions de participation")
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                RequirementItem("Qualification dans le domaine BTP")
                                RequirementItem("Certificat de conformité fiscale")
                                RequirementItem("Références de projets similaires")
                                RequirementItem("Capacité financière minimale: 100M DZD")
                            }
                        }
                    }
                }
                1 -> {
                    item {
                        // Documents Content
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = AlMizanColors.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                DocumentItem(
                                    name = "Cahier des charges",
                                    size = "2.4 MB",
                                    type = "PDF"
                                )
                                
                                Divider(modifier = Modifier.padding(vertical = 12.dp))
                                
                                DocumentItem(
                                    name = "Plans architecturaux",
                                    size = "8.7 MB",
                                    type = "PDF"
                                )
                                
                                Divider(modifier = Modifier.padding(vertical = 12.dp))
                                
                                DocumentItem(
                                    name = "Devis quantitatif",
                                    size = "1.2 MB",
                                    type = "XLSX"
                                )
                                
                                Divider(modifier = Modifier.padding(vertical = 12.dp))
                                
                                DocumentItem(
                                    name = "Règlement de consultation",
                                    size = "850 KB",
                                    type = "PDF"
                                )
                            }
                        }
                    }
                }
                2 -> {
                    item {
                        // Contact Content
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = AlMizanColors.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                SectionTitle("Service contractant")
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                ContactItem(
                                    icon = Icons.Default.Business,
                                    label = "Organisation",
                                    value = "Direction de l'Éducation - Wilaya d'Alger"
                                )
                                
                                ContactItem(
                                    icon = Icons.Default.Person,
                                    label = "Contact",
                                    value = "M. Ahmed Benali"
                                )
                                
                                ContactItem(
                                    icon = Icons.Default.Email,
                                    label = "Email",
                                    value = "contact.education@alger.dz"
                                )
                                
                                ContactItem(
                                    icon = Icons.Default.Phone,
                                    label = "Téléphone",
                                    value = "+213 21 XX XX XX"
                                )
                                
                                ContactItem(
                                    icon = Icons.Default.LocationOn,
                                    label = "Adresse",
                                    value = "Boulevard Mohamed V, Alger Centre"
                                )
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
                
                // Action Buttons
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AlMizanButton(
                        text = "Soumettre une offre",
                        onClick = { /* Submit */ },
                        modifier = Modifier.fillMaxWidth(),
                        variant = ButtonVariant.Primary
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedButton(
                        onClick = { /* Download all */ },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = AlMizanColors.NavySovereign
                        )
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Télécharger tous les documents")
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun InfoColumn(
    label: String,
    value: String,
    icon: String
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = AlMizanPastelColors.BlueClair
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = AlMizanColors.White
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = AlMizanColors.NavySovereign
    )
}

@Composable
private fun SpecificationItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = AlMizanPastelColors.TextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = AlMizanPastelColors.TextPrimary
        )
    }
}

@Composable
private fun RequirementItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(AlMizanColors.GoldAlMizan)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = AlMizanPastelColors.TextPrimary
        )
    }
}

@Composable
private fun DocumentItem(
    name: String,
    size: String,
    type: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AlMizanPastelColors.BeigeDore.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = type,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = AlMizanColors.Bronze
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = AlMizanPastelColors.TextPrimary
                )
                Text(
                    text = size,
                    style = MaterialTheme.typography.bodySmall,
                    color = AlMizanPastelColors.TextTertiary
                )
            }
        }
        
        IconButton(onClick = { /* Download */ }) {
            Icon(
                Icons.Default.Download,
                contentDescription = "Télécharger",
                tint = AlMizanColors.GoldAlMizan
            )
        }
    }
}

@Composable
private fun ContactItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = AlMizanPastelColors.BluePrincipal,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = AlMizanPastelColors.TextTertiary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = AlMizanPastelColors.TextPrimary
            )
        }
    }
}
