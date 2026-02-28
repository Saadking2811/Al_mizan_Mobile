package dz.gov.almizan.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dz.gov.almizan.ui.theme.AlMizanColors
import dz.gov.almizan.ui.theme.AlMizanPastelColors

data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val type: NotificationType,
    var isRead: Boolean = false
)

enum class NotificationType { TENDER, DEADLINE, SYSTEM, AWARD, INFO }

private val SAMPLE_NOTIFICATIONS = listOf(
    AppNotification("1", "Nouvel appel d'offres", "AO N°2025-038 publié : Fourniture de matériel informatique - Ministère de l'Enseignement Supérieur", "Il y a 10 min", NotificationType.TENDER, false),
    AppNotification("2", "Délai critique — 48h", "L'appel d'offres N°2025-021 expire dans 48 heures. Soumettez votre offre avant le 02/03/2026", "Il y a 1h", NotificationType.DEADLINE, false),
    AppNotification("3", "Attribution de marché", "Vous avez été sélectionné pour le marché N°2024-187 - Travaux de réhabilitation", "Il y a 3h", NotificationType.AWARD, false),
    AppNotification("4", "AO modifié", "Des modifications ont été apportées à l'AO N°2025-019. Consultez les nouveaux documents.", "Hier, 14:30", NotificationType.TENDER, true),
    AppNotification("5", "Mise à jour système", "La plateforme Al-Mizan a été mise à jour vers la version 2.4.1. Nouvelles fonctionnalités disponibles.", "Hier, 09:00", NotificationType.SYSTEM, true),
    AppNotification("6", "Rappel de conformité", "Votre dossier de qualification doit être renouvelé avant le 15/03/2026.", "Avant-hier", NotificationType.INFO, true),
    AppNotification("7", "Nouvel appel d'offres", "AO N°2025-035 : Construction d'un complexe sportif - Wilaya de Sétif", "Il y a 2 jours", NotificationType.TENDER, true),
    AppNotification("8", "Résultats publiés", "Les résultats de l'appel d'offres N°2024-203 sont disponibles.", "Il y a 3 jours", NotificationType.AWARD, true),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen() {
    var notifications by remember { mutableStateOf(SAMPLE_NOTIFICATIONS.toMutableList()) }
    var selectedFilter by remember { mutableStateOf("Tous") }
    val filters = listOf("Tous", "Non lus", "AO", "Délais", "Attributions")

    val unreadCount = notifications.count { !it.isRead }

    val filtered = when (selectedFilter) {
        "Non lus" -> notifications.filter { !it.isRead }
        "AO" -> notifications.filter { it.type == NotificationType.TENDER }
        "Délais" -> notifications.filter { it.type == NotificationType.DEADLINE }
        "Attributions" -> notifications.filter { it.type == NotificationType.AWARD }
        else -> notifications
    }

    Column(modifier = Modifier.fillMaxSize().background(AlMizanPastelColors.Background)) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(AlMizanColors.NavySovereign, AlMizanColors.BlueRoyal)
                    )
                )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Notifications",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = AlMizanColors.White
                        )
                        if (unreadCount > 0) {
                            Text(
                                "$unreadCount non lue${if (unreadCount > 1) "s" else ""}",
                                style = MaterialTheme.typography.bodySmall,
                                color = AlMizanColors.GoldAlMizan
                            )
                        }
                    }
                    if (unreadCount > 0) {
                        TextButton(
                            onClick = {
                                notifications = notifications.map { it.copy(isRead = true) }.toMutableList()
                            }
                        ) {
                            Text(
                                "Tout lire",
                                style = MaterialTheme.typography.labelMedium,
                                color = AlMizanColors.GoldAlMizan
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Filter chips
                androidx.compose.foundation.lazy.LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filters.size) { i ->
                        val f = filters[i]
                        FilterChip(
                            selected = selectedFilter == f,
                            onClick = { selectedFilter = f },
                            label = {
                                Text(
                                    f,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AlMizanColors.GoldAlMizan,
                                selectedLabelColor = AlMizanColors.NavySovereign,
                                containerColor = AlMizanColors.White.copy(alpha = 0.1f),
                                labelColor = AlMizanColors.White
                            )
                        )
                    }
                }
            }
        }

        // Notification list
        if (filtered.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.NotificationsNone,
                        contentDescription = null,
                        tint = AlMizanColors.Gray300,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Aucune notification",
                        style = MaterialTheme.typography.titleMedium,
                        color = AlMizanColors.Gray400
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(filtered) { index, notif ->
                    var visible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(index * 50L)
                        visible = true
                    }
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn() + slideInVertically { it / 3 }
                    ) {
                        NotificationItem(
                            notification = notif,
                            onRead = {
                                notifications = notifications.map {
                                    if (it.id == notif.id) it.copy(isRead = true) else it
                                }.toMutableList()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(
    notification: AppNotification,
    onRead: () -> Unit
) {
    val typeColor = when (notification.type) {
        NotificationType.TENDER -> AlMizanColors.Info
        NotificationType.DEADLINE -> AlMizanColors.Warning
        NotificationType.AWARD -> AlMizanColors.SuccessLight
        NotificationType.SYSTEM -> AlMizanColors.GoldAlMizan
        NotificationType.INFO -> AlMizanColors.BlueSky
    }
    val typeIcon: ImageVector = when (notification.type) {
        NotificationType.TENDER -> Icons.Default.Description
        NotificationType.DEADLINE -> Icons.Default.Schedule
        NotificationType.AWARD -> Icons.Default.EmojiEvents
        NotificationType.SYSTEM -> Icons.Default.Settings
        NotificationType.INFO -> Icons.Default.Info
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRead() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!notification.isRead)
                AlMizanColors.White else AlMizanPastelColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (!notification.isRead) 3.dp else 1.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(typeColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    typeIcon,
                    contentDescription = null,
                    tint = typeColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        notification.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (!notification.isRead) FontWeight.Bold else FontWeight.Medium,
                        color = AlMizanColors.NavySovereign,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        notification.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = AlMizanColors.Gray400
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    notification.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = AlMizanColors.Gray500,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }

            // Unread dot
            if (!notification.isRead) {
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(AlMizanColors.GoldAlMizan)
                        .align(Alignment.Top)
                )
            }
        }
    }
}
