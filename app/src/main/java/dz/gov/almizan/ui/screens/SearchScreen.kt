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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dz.gov.almizan.model.Tender
import dz.gov.almizan.ui.components.TenderCard
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onTenderClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Tous") }
    var selectedStatus by remember { mutableStateOf("Tous") }
    var showFilters by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recherche avancée") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AlMizanColors.NavySovereign,
                    titleContentColor = AlMizanColors.White,
                    navigationIconContentColor = AlMizanColors.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AlMizanPastelColors.Background)
                .padding(paddingValues)
        ) {
            // Search Bar
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
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Mots-clés, numéro AO...") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(onClick = { showFilters = !showFilters }) {
                            Icon(
                                Icons.Default.FilterList,
                                contentDescription = "Filtres",
                                tint = if (showFilters) AlMizanColors.GoldAlMizan 
                                       else AlMizanPastelColors.TextSecondary
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
            
            // Filters Panel
            if (showFilters) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = AlMizanColors.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Filtres",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AlMizanColors.NavySovereign
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Category Filter
                        Text(
                            text = "Catégorie",
                            style = MaterialTheme.typography.labelMedium,
                            color = AlMizanPastelColors.TextSecondary
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Tous", "Travaux", "Services", "Fourniture").forEach { category ->
                                FilterChip(
                                    selected = selectedCategory == category,
                                    onClick = { selectedCategory = category },
                                    label = { Text(category) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = AlMizanColors.GoldAlMizan,
                                        selectedLabelColor = AlMizanColors.White
                                    )
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Status Filter
                        Text(
                            text = "Statut",
                            style = MaterialTheme.typography.labelMedium,
                            color = AlMizanPastelColors.TextSecondary
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Tous", "OUVERT", "EN_COURS", "FERMÉ").forEach { status ->
                                FilterChip(
                                    selected = selectedStatus == status,
                                    onClick = { selectedStatus = status },
                                    label = { Text(status) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = AlMizanPastelColors.BluePrincipal,
                                        selectedLabelColor = AlMizanColors.White
                                    )
                                )
                            }
                        }
                    }
                }
            }
            
            // Results Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${searchResults.size} résultats trouvés",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AlMizanPastelColors.TextSecondary
                )
                
                IconButton(onClick = { /* Sort */ }) {
                    Icon(
                        Icons.Default.Sort,
                        contentDescription = "Trier",
                        tint = AlMizanPastelColors.TextSecondary
                    )
                }
            }
            
            // Results List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(searchResults) { tender ->
                    TenderCard(
                        title = tender.title,
                        reference = tender.reference,
                        budget = tender.budget,
                        deadline = tender.deadline,
                        status = tender.status,
                        onClick = { onTenderClick(tender.id) }
                    )
                }
            }
        }
    }
}

// Sample search results
private val searchResults = listOf(
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
    ),
    Tender(
        "4",
        "Services de maintenance hospitalière",
        "AO-2026-004",
        "120M DZD",
        "18 Mars 2026",
        "OUVERT"
    ),
    Tender(
        "5",
        "Étude technique projet autoroutier",
        "AO-2026-005",
        "95M DZD",
        "28 Mars 2026",
        "OUVERT"
    )
)
