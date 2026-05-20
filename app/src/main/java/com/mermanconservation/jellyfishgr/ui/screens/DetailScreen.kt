package com.mermanconservation.jellyfishgr.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mermanconservation.jellyfishgr.R
import com.mermanconservation.jellyfishgr.data.DangerLevel
import com.mermanconservation.jellyfishgr.data.getInatUrl
import com.mermanconservation.jellyfishgr.data.getSpeciesById
import com.mermanconservation.jellyfishgr.ui.theme.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(speciesId: String, onBack: () -> Unit) {
    val species = getSpeciesById(speciesId) ?: return
    val isGreek = Locale.getDefault().language == "el"
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val dangerColor = when (species.dangerLevel) {
        DangerLevel.HARMLESS -> SafeGreen
        DangerLevel.MILD -> WarnAmber
        DangerLevel.STRONG -> DangerRed
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = species.scientificName,
                        fontStyle = FontStyle.Italic,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OceanBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            // Hero image area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(OceanBlueDark, OceanBlue.copy(alpha = 0.6f))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Try to load an iNaturalist taxon image
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("https://www.inaturalist.org/taxa/${species.inaturalistTaxonId}/default_photo.jpg")
                        .crossfade(true)
                        .build(),
                    contentDescription = species.scientificName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Gradient overlay for text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                            )
                        )
                )
                // Fallback jellyfish emoji (visible if image fails to load)
                Text(text = "🪼", fontSize = 72.sp)
                // Name overlay at bottom
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = species.scientificName,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = if (isGreek) species.commonNameEl else species.commonNameEn,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                // Badges row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    // Danger level badge
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = dangerColor.copy(alpha = 0.15f),
                        tonalElevation = 0.dp
                    ) {
                        Text(
                            text = "${species.dangerLevel.emoji} ${if (isGreek) species.dangerLevel.labelEl else species.dangerLevel.labelEn}",
                            style = MaterialTheme.typography.labelLarge,
                            color = dangerColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                    // Alien species badge
                    if (species.isAlienSpecies) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = CoralOrange.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = if (isGreek) "⚠ Ξενικό Είδος" else "⚠ Alien Species",
                                style = MaterialTheme.typography.labelLarge,
                                color = CoralOrange,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                    // Not true jellyfish badge
                    if (!species.isTrueJellyfish) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = if (isGreek) "Δεν είναι μέδουσα" else "Not a jellyfish",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Quick stats grid
                DetailStatsGrid(species = species, isGreek = isGreek)

                Spacer(modifier = Modifier.height(16.dp))

                // Biology section
                DetailSection(
                    icon = Icons.Filled.Science,
                    title = if (isGreek) "Βιολογία" else "Biology",
                    content = if (isGreek) species.bioEl else species.bioEn,
                    containerColor = OceanBlue.copy(alpha = 0.07f)
                )

                // First aid section (only for species with sting info)
                if (species.firstAidEn.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailSection(
                        icon = Icons.Filled.MedicalServices,
                        title = if (isGreek) "Πρώτες Βοήθειες" else "First Aid",
                        content = if (isGreek) species.firstAidEl else species.firstAidEn,
                        containerColor = dangerColor.copy(alpha = 0.07f),
                        titleColor = dangerColor
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // iNaturalist button
                OutlinedButton(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getInatUrl(species.inaturalistTaxonId))
                        )
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Filled.OpenInBrowser,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (isGreek) "Παρατηρήσεις στο iNaturalist"
                        else "View on iNaturalist"
                    )
                }

                // Report sighting button
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.inaturalist.org/observations/upload")
                        )
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = SeaGreen)
                ) {
                    Text(
                        if (isGreek) "📷 Καταχωρίστε Παρατήρηση"
                        else "📷 Report a Sighting"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isGreek)
                        "Πηγή: BiodiversityGR · biodiversitygr.eu · CC BY-NC 3.0 GR"
                    else
                        "Source: BiodiversityGR · biodiversitygr.eu · CC BY-NC 3.0 GR",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun DetailStatsGrid(species: com.mermanconservation.jellyfishgr.data.JellyfishSpecies, isGreek: Boolean) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                StatCell(
                    label = if (isGreek) "Μέγιστο μέγεθος" else "Max size",
                    value = "${species.maxSizeCm} cm",
                    modifier = Modifier.weight(1f)
                )
                StatCell(
                    label = if (isGreek) "Βάθος" else "Depth",
                    value = "${species.depthM} m",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                StatCell(
                    label = if (isGreek) "Θερμοκρασία" else "Temp. range",
                    value = species.tempRange,
                    modifier = Modifier.weight(1f)
                )
                StatCell(
                    label = if (isGreek) "Ενδιαίτημα" else "Habitat",
                    value = if (isGreek) species.habitatEl else species.habitatEn,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun StatCell(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DetailSection(
    icon: ImageVector,
    title: String,
    content: String,
    containerColor: Color,
    titleColor: Color = OceanBlue
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = titleColor,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = titleColor,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp
            )
        }
    }
}
