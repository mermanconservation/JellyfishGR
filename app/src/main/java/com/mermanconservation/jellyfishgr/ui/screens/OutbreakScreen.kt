package com.mermanconservation.jellyfishgr.ui.screens

import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.mermanconservation.jellyfishgr.ui.theme.*
import java.util.Locale

@Composable
fun OutbreakScreen() {
    val isGreek = Locale.getDefault().language == "el"
    val context = LocalContext.current
    var showWebView by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(PelagiaPurple, PelagiaPink)
                    )
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "🪼", fontSize = 56.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pelagia noctiluca",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isGreek) "Παρακολούθηση Εξάρσεων · Ελλάδα 2026"
                    else "Bloom Monitoring · Greece 2026",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isGreek) "Pelagia Logbook · Merman Conservation"
                    else "Pelagia Logbook · Merman Conservation",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {

            // About the outbreak card
            InfoCard(
                icon = "🌊",
                title = if (isGreek) "Τι είναι η Έξαρση;" else "What is a Bloom?",
                content = if (isGreek)
                    "Η Pelagia noctiluca (μωβ μέδουσα) παρουσιάζει κυκλικές μαζικές εξάρσεις στη Μεσόγειο. Κατά τη διάρκεια εξάρσεων, εκατοντάδες χιλιάδες ατόμων μπορεί να καλύψουν παράκτιες περιοχές, επηρεάζοντας τον τουρισμό, την αλιεία και τα οικοσυστήματα."
                else
                    "Pelagia noctiluca (Mauve Stinger) undergoes cyclical mass bloom events in the Mediterranean. During blooms, hundreds of thousands of individuals can cover coastal areas, impacting tourism, fisheries and ecosystems."
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoCard(
                icon = "📅",
                title = if (isGreek) "Εποχικότητα" else "Seasonality",
                content = if (isGreek)
                    "Οι εξάρσεις εντοπίζονται κυρίως από τον Ιούλιο έως τον Σεπτέμβριο. Ευνοούνται από υψηλές θερμοκρασίες νερού, ηρεμία θάλασσας και ισχυρά ρεύματα. Οι βόρειοι άνεμοι συχνά ωθούν τα κοπάδια στις ακτές."
                else
                    "Blooms are observed mainly from July to September. They are favoured by high water temperatures, calm seas and strong currents. North winds frequently push aggregations toward the coast."
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Danger warning card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DangerRed.copy(alpha = 0.08f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Filled.Warning,
                            contentDescription = null,
                            tint = DangerRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = if (isGreek) "Πρώτες Βοήθειες — Μωβ Μέδουσα"
                            else "First Aid — Mauve Stinger",
                            style = MaterialTheme.typography.titleSmall,
                            color = DangerRed,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    FirstAidStep(
                        step = "1",
                        textEl = "ΜΗΝ χρησιμοποιήσετε ξύδι — επιδεινώνει το τσίμπημα.",
                        textEn = "Do NOT use vinegar — it worsens the sting.",
                        isGreek = isGreek
                    )
                    FirstAidStep(
                        step = "2",
                        textEl = "Ξεπλύνετε ΜΟΝΟ με θαλασσινό νερό, ΟΧΙ γλυκό.",
                        textEn = "Rinse ONLY with sea water — NOT fresh water.",
                        isGreek = isGreek
                    )
                    FirstAidStep(
                        step = "3",
                        textEl = "Αφαιρέστε πλοκάμια με πλαστική κάρτα (ποτέ με χέρια).",
                        textEn = "Remove tentacles with a plastic card — never with hands.",
                        isGreek = isGreek
                    )
                    FirstAidStep(
                        step = "4",
                        textEl = "Εφαρμόστε πάστα μαγειρικής σόδας & θαλασσινού νερού (1:1).",
                        textEn = "Apply baking soda and sea water paste (1:1 ratio).",
                        isGreek = isGreek
                    )
                    FirstAidStep(
                        step = "5",
                        textEl = "Ζητήστε ιατρική βοήθεια αν το τσίμπημα είναι εκτεταμένο.",
                        textEn = "Seek medical attention if the sting is extensive.",
                        isGreek = isGreek
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pelagia Logbook buttons
            Text(
                text = if (isGreek) "Pelagia Logbook 2026" else "Pelagia Logbook 2026",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PelagiaPurple
            )
            Text(
                text = if (isGreek)
                    "Πλατφόρμα παρακολούθησης εξάρσεων μωβ μέδουσας. Καταχωρήστε τις παρατηρήσεις σας!"
                else
                    "Purple jellyfish bloom monitoring platform. Submit your sightings!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Toggle embedded webview
            Button(
                onClick = { showWebView = !showWebView },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PelagiaPurple)
            ) {
                Icon(
                    Icons.Filled.Map,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    if (showWebView)
                        (if (isGreek) "Απόκρυψη Χάρτη" else "Hide Map")
                    else
                        (if (isGreek) "Άνοιγμα Χάρτη Εξάρσεων" else "Open Bloom Map")
                )
            }

            // Embedded Pelagia Logbook WebView
            if (showWebView) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    tonalElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                ) {
                    AndroidView(
                        factory = { ctx ->
                            WebView(ctx).apply {
                                webViewClient = object : WebViewClient() {
                                    override fun shouldOverrideUrlLoading(
                                        view: WebView?,
                                        request: WebResourceRequest?
                                    ): Boolean = false
                                }
                                settings.apply {
                                    javaScriptEnabled = true
                                    domStorageEnabled = true
                                    loadWithOverviewMode = true
                                    useWideViewPort = true
                                }
                                loadUrl("https://pelagia.mermanconservation.co.uk/")
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Open in browser button
            OutlinedButton(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://pelagia.mermanconservation.co.uk/")
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
                    if (isGreek) "Άνοιγμα στο Browser"
                    else "Open in Browser"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // iNaturalist project link
            OutlinedButton(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.inaturalist.org/projects/jellyfish-of-greece")
                    )
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (isGreek) "🔬 Jellyfish of Greece · iNaturalist Project"
                    else "🔬 Jellyfish of Greece · iNaturalist Project"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isGreek)
                    "Merman Conservation Expeditions Ltd · mermanconservation.co.uk\nPelagia Logbook 2026"
                else
                    "Merman Conservation Expeditions Ltd · mermanconservation.co.uk\nPelagia Logbook 2026",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun InfoCard(icon: String, title: String, content: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = OceanBlue.copy(alpha = 0.07f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Text(text = icon, fontSize = 18.sp)
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = OceanBlue
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

@Composable
fun FirstAidStep(step: String, textEl: String, textEn: String, isGreek: Boolean) {
    Row(
        modifier = Modifier.padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = RoundedCornerShape(50),
            color = DangerRed,
            modifier = Modifier.size(20.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = step,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text = if (isGreek) textEl else textEn,
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 18.sp
        )
    }
}
