package com.mermanconservation.jellyfishgr.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mermanconservation.jellyfishgr.R
import com.mermanconservation.jellyfishgr.data.DangerLevel
import com.mermanconservation.jellyfishgr.data.JellyfishSpecies
import com.mermanconservation.jellyfishgr.data.jellyfishDatabase
import com.mermanconservation.jellyfishgr.ui.theme.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeciesListScreen(onSpeciesClick: (String) -> Unit) {
    val isGreek = Locale.getDefault().language == "el"
    var searchQuery by remember { mutableStateOf("") }
    var filterAlien by remember { mutableStateOf<Boolean?>(null) }
    var filterDanger by remember { mutableStateOf<DangerLevel?>(null) }
    var showFilters by remember { mutableStateOf(false) }

    val filtered = remember(searchQuery, filterAlien, filterDanger) {
        jellyfishDatabase.filter { sp ->
            val q = searchQuery.lowercase()
            val matchesSearch = q.isEmpty() ||
                sp.scientificName.lowercase().contains(q) ||
                sp.commonNameEn.lowercase().contains(q) ||
                sp.commonNameEl.lowercase().contains(q)
            val matchesAlien = filterAlien == null || sp.isAlienSpecies == filterAlien
            val matchesDanger = filterDanger == null || sp.dangerLevel == filterDanger
            matchesSearch && matchesAlien && matchesDanger
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(listOf(OceanBlueDark, OceanBlue))
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column {
                Text(
                    text = if (isGreek) "Οδηγός Μεδουσών Ελλάδας" else "Jellyfish Field Guide — Greece",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isGreek) "${jellyfishDatabase.size} είδη · Πηγή: BiodiversityGR"
                    else "${jellyfishDatabase.size} species · Source: BiodiversityGR",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.75f)
                )
            }
        }

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            placeholder = {
                Text(stringResource(R.string.search_hint))
            },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            trailingIcon = {
                Row {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Filled.Close, contentDescription = "Clear")
                        }
                    }
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Icon(
                            Icons.Filled.FilterList,
                            contentDescription = "Filters",
                            tint = if (filterAlien != null || filterDanger != null)
                                MaterialTheme.colorScheme.primary else LocalContentColor.current
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        // Filter chips
        AnimatedVisibility(visible = showFilters) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                Text(
                    text = if (isGreek) "Φίλτρα" else "Filters",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Danger level filters
                    items(DangerLevel.entries) { level ->
                        FilterChip(
                            selected = filterDanger == level,
                            onClick = { filterDanger = if (filterDanger == level) null else level },
                            label = { Text("${level.emoji} ${if (isGreek) level.labelEl else level.labelEn}") }
                        )
                    }
                    // Alien filter
                    item {
                        FilterChip(
                            selected = filterAlien == true,
                            onClick = { filterAlien = if (filterAlien == true) null else true },
                            label = { Text(if (isGreek) "Ξενικά" else "Alien") }
                        )
                    }
                    item {
                        FilterChip(
                            selected = filterAlien == false,
                            onClick = { filterAlien = if (filterAlien == false) null else false },
                            label = { Text(if (isGreek) "Ενδημικά" else "Native") }
                        )
                    }
                }
            }
        }

        // Result count
        Text(
            text = if (isGreek) "${filtered.size} αποτελέσματα" else "${filtered.size} results",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filtered, key = { it.id }) { species ->
                SpeciesCard(
                    species = species,
                    isGreek = isGreek,
                    onClick = { onSpeciesClick(species.id) }
                )
            }
        }
    }
}

@Composable
fun SpeciesCard(species: JellyfishSpecies, isGreek: Boolean, onClick: () -> Unit) {
    val dangerColor = when (species.dangerLevel) {
        DangerLevel.HARMLESS -> SafeGreen
        DangerLevel.MILD -> WarnAmber
        DangerLevel.STRONG -> DangerRed
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Danger level colour bar
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight()
                    .background(dangerColor)
            )

            // Species image (from iNaturalist)
            val imageUrl = "https://www.inaturalist.org/photos/medium/${species.inaturalistTaxonId}.jpg"
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://www.inaturalist.org/taxa/${species.inaturalistTaxonId}/default_photo.jpg")
                        .crossfade(true)
                        .build(),
                    contentDescription = species.scientificName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Fallback emoji overlay if no image
                Text(
                    text = "🪼",
                    fontSize = 28.sp
                )
            }

            // Text info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = species.scientificName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (species.isAlienSpecies) {
                        Text(
                            text = "⚠",
                            fontSize = 12.sp,
                            color = CoralOrange
                        )
                    }
                }
                Text(
                    text = if (isGreek) species.commonNameEl else species.commonNameEn,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!species.isTrueJellyfish) {
                    Text(
                        text = if (isGreek) "Δεν είναι αληθινή μέδουσα" else "Not a true jellyfish",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontStyle = FontStyle.Italic
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Danger badge
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = dangerColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "${species.dangerLevel.emoji} ${if (isGreek) species.dangerLevel.labelEl else species.dangerLevel.labelEn}",
                            style = MaterialTheme.typography.labelSmall,
                            color = dangerColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    // Size
                    Text(
                        text = "📏 ${species.maxSizeCm} cm",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
