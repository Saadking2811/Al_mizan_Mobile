package dz.gov.almizan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dz.gov.almizan.R
import dz.gov.almizan.model.Tender
import dz.gov.almizan.ui.components.*
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    onTenderClick: (String) -> Unit,
    onDashboardClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
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
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.logo_almizan),
                                contentDescription = "Logo Al-Mizan",
                                modifier = Modifier.size(40.dp),
                                tint = Color.Unspecified
                            )
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            Column {
                                Text(
                                    text = "Al-Mizan",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = AlMizanColors.White
                                )
                                Text(
                                    text = "Marchés Publics",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AlMizanPastelColors.BlueClair
                                )
                            }
                        }
                        
                        Row {
                            IconButton(onClick = { /* Notifications */ }) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = AlMizanColors.White
                                )
                            }
                            IconButton(onClick = onDashboardClick) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Profil",
                                    tint = AlMizanColors.White
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Rechercher un appel d'offres...") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            IconButton(onClick = onSearchClick) {
                                Icon(
                                    Icons.Default.FilterList,
                                    contentDescription = "Filtres"
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = AlMizanColors.White,
                            focusedContainerColor = AlMizanColors.White,
                            unfocusedBorderColor = AlMizanColors.White,
                            focusedBorderColor = AlMizanColors.GoldAlMizan
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AlMizanPastelColors.Background)
                .padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Stats Row
                Text(
                    text = "Vue d'ensemble",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AlMizanColors.NavySovereign,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "AO OUVERTS",
                        value = "124",
                        icon = "📊",
                        accentColor = AlMizanColors.GoldAlMizan,
                        modifier = Modifier.weight(1f)
                    )
                    
                    StatCard(
                        title = "EN COURS",
                        value = "8",
                        icon = "⏳",
                        accentColor = AlMizanPastelColors.BluePrincipal,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            item {
                // Quick Actions
                Text(
                    text = "Actions rapides",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AlMizanColors.NavySovereign,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(quickActions) { action ->
                        QuickActionCard(
                            title = action.title,
                            icon = action.icon,
                            color = action.color,
                            onClick = { /* Handle action */ }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Appels d'offres récents",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AlMizanColors.NavySovereign
                    )
                    
                    TextButton(onClick = onSearchClick) {
                        Text(
                            text = "Voir tout",
                            color = AlMizanColors.GoldAlMizan
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            items(sampleTenders) { tender ->
                TenderCard(
                    title = tender.title,
                    reference = tender.reference,
                    budget = tender.budget,
                    deadline = tender.deadline,
                    status = tender.status,
                    onClick = { onTenderClick(tender.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuickActionCard(
    title: String,
    icon: String,
    color: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(140.dp)
            .height(100.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AlMizanColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = AlMizanPastelColors.TextPrimary
            )
        }
    }
}

// Sample Data
data class QuickAction(
    val title: String,
    val icon: String,
    val color: androidx.compose.ui.graphics.Color
)

private val quickActions = listOf(
    QuickAction("Recherche", "🔍", AlMizanPastelColors.BluePrincipal),
    QuickAction("Favoris", "⭐", AlMizanColors.GoldAlMizan),
    QuickAction("Documents", "📄", AlMizanPastelColors.GrisBleu),
    QuickAction("Aide", "❓", AlMizanPastelColors.InfoPastel)
)

private val sampleTenders = listOf(
    Tender(
        "1",
        "Construction d'un lycée à Alger",
        "AO-2026-001",
        "450M DZD",
        "15 Mars 2026",
        "OUVERT"
    ),
    Tender(
        "2",
        "Fourniture de matériel informatique",
        "AO-2026-002",
        "85M DZD",
        "20 Mars 2026",
        "OUVERT"
    ),
    Tender(
        "3",
        "Rénovation de routes à Oran",
        "AO-2026-003",
        "320M DZD",
        "25 Mars 2026",
        "EN_COURS"
    )
)
