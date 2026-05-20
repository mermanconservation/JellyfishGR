# Jellyfish Greece — Android Field Guide

**Package:** `com.mermanconservation.jellyfishgr`
**Produced by:** Merman Conservation Expeditions Ltd
**Species data source:** BiodiversityGR (biodiversitygr.eu) · CC BY-NC 3.0 GR
**Outbreak platform:** Pelagia Logbook 2026 (pelagia.mermanconservation.co.uk)

---

## Features

- 41 species / jellyfish-like organisms recorded in Greek waters
- Greek and English — auto-switches with device system language
- Search bar + filter chips (danger level, alien/native)
- Per-species: biology, danger rating, first aid, size, depth, temperature range
- Pelagia noctiluca outbreak screen with embedded Pelagia Logbook map
- First aid protocol for Mauve Stinger (specific warning: no vinegar)
- iNaturalist deep-links for observations and reporting
- Ocean-themed Material 3 design, dark mode ready

---

## Setup in Android Studio

### 1. Prerequisites

| Tool | Version |
|------|---------|
| Android Studio | Hedgehog 2023.1.1 or newer |
| Kotlin | 2.0.0 |
| Android Gradle Plugin | 8.5.0 |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 35 |

### 2. Open the project

1. Clone or extract this folder.
2. Open Android Studio.
3. Choose **File > Open** and select the `JellyfishGR` folder.
4. Wait for Gradle sync to complete.
5. Run on a device or emulator (API 26+).

### 3. Key dependencies (auto-downloaded by Gradle)

```
androidx.navigation:navigation-compose:2.7.7
io.coil-kt:coil-compose:2.7.0
androidx.compose:compose-bom:2024.08.00
androidx.compose.material:material-icons-extended
```

---

## Project Structure

```
app/src/main/
├── java/com/mermanconservation/jellyfishgr/
│   ├── MainActivity.kt              Navigation host, bottom bar
│   ├── data/
│   │   └── JellyfishData.kt         All 41 species, data model, enums
│   └── ui/
│       ├── theme/
│       │   └── Theme.kt             Ocean colour palette, Material3
│       └── screens/
│           ├── SpeciesListScreen.kt Search, filter, species cards
│           ├── DetailScreen.kt      Biology, first aid, stats, links
│           └── OutbreakScreen.kt    Pelagia bloom dashboard + WebView
└── res/
    ├── values/strings.xml           English strings
    └── values-el/strings.xml        Greek strings
```

---

## Species included (41 total)

| # | Scientific name | Common name EN | Danger | Alien |
|---|----------------|---------------|--------|-------|
| 1 | Aequorea forskalea | Many-ribbed Jellyfish | Harmless | No |
| 2 | Aurelia aurita | Moon Jelly | Mild | No |
| 3 | Aurelia solida | Moon Jelly (alien) | Mild | Yes |
| 4 | Beroe forskalii | Beroe Comb Jelly | Harmless | No |
| 5 | Beroe ovata | Beroe Comb Jelly | Harmless | Yes |
| 6 | Callianira bialata | Callianira Comb Jelly | Harmless | No |
| 7 | Carybdea marsupialis | Marine Stinger / Sea Wasp | Strong | No |
| 8 | Cassiopea andromeda | Upside-down Jellyfish | Strong | Yes |
| 9 | Cestum veneris | Venus's Girdle | Harmless | No |
| 10 | Chrysaora hysoscella | Compass Jellyfish | Strong | No |
| 11 | Cotylorhiza tuberculata | Fried Egg Jellyfish | Mild | No |
| 12 | Craspedacusta sowerbii | Freshwater Jellyfish | Mild | Yes |
| 13 | Deiopea kaloktenota | Deiopea Comb Jelly | Harmless | No |
| 14 | Discomedusa lobata | Discomedusa | Mild | No |
| 15 | Drymonema dalmatinum | Pink Meanie | Strong | No |
| 16 | Geryonia proboscidalis | Geryonia | Strong | No |
| 17 | Haeckelia bimaculata | Haeckelia Comb Jelly | Strong | No |
| 18 | Hormiphora plumosa | Hormiphora Comb Jelly | Harmless | No |
| 19 | Leuckartiara octona | Leuckartiara Hydromedusa | Harmless | No |
| 20 | Leucothea multicornis | Leucothea Comb Jelly | Harmless | No |
| 21 | Liriope tetraphylla | Liriope Hydromedusa | Strong | No |
| 22 | Mawia benovici | Mawia Jellyfish | Strong | Yes |
| 23 | Mnemiopsis leidyi | Warty Comb Jelly / Sea Walnut | Harmless | Yes |
| 24 | Oceania armata | Oceania Hydromedusa | Mild | No |
| 25 | Olindias muelleri | Cigar Jellyfish | Strong | No |
| 26 | Orchistoma pileus | Orchistoma Hydromedusa | Mild | No |
| 27 | Pelagia noctiluca | Purple-striped Jellyfish / Mauve Stinger | Strong | No |
| 28 | Persa incolorata | Persa Jellyfish | Strong | No |
| 29 | Phyllorhiza punctata | White-spotted Jellyfish | Mild | Yes |
| 30 | Physalia physalis | Portuguese Man-of-War | Strong | No |
| 31 | Pleurobranchia pileus | Sea Gooseberry | Harmless | No |
| 32 | Porpita porpita | Blue Button | Harmless | No |
| 33 | Pyrosoma atlanticum | Pyrosome | Harmless | No |
| 34 | Rhizostoma luteum | Yellow Barrel Jellyfish | Mild | No |
| 35 | Rhizostoma pulmo | Barrel Jellyfish | Mild | No |
| 36 | Rhopilema nomadica | Nomad Jellyfish | Strong | Yes |
| 37 | Salpa fusiformis | Common Salp | Harmless | No |
| 38 | Salpa maxima | Giant Salp | Harmless | No |
| 39 | Solmissus albescens | Solmissus Jellyfish | Mild | No |
| 40 | Thalia democratica | Fire Salp | Harmless | No |
| 41 | Velella velella | By-the-wind Sailor | Harmless | No |

---

## Building the APK and AAB

### Option A — GitHub Actions (easiest, no local setup needed)

1. Create a new GitHub repository.
2. Push this project to it:
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin https://github.com/YOUR_USERNAME/JellyfishGR.git
   git push -u origin main
   ```
3. GitHub Actions runs automatically. Go to **Actions** tab in your repo.
4. When the workflow finishes, click the run and scroll to **Artifacts** at the bottom.
5. Download `JellyfishGR-debug.apk`, `JellyfishGR-release-unsigned.apk`, and `JellyfishGR-release.aab`.

The workflow file is at `.github/workflows/build.yml`.

---

### Option B — Local build (Android Studio or command line)

**Prerequisites:**
- Android Studio Hedgehog (2023.1.1) or newer — installs the SDK automatically
- JDK 17 or 21
- Accept the SDK licence when prompted on first sync

**Step 1 — Generate the Gradle wrapper jar** (one-time, inside the project folder):
```bash
gradle wrapper --gradle-version 8.7
```
Or just open the project in Android Studio — it will offer to download the wrapper automatically.

**Step 2 — Build the debug APK** (sideload / test on device):
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

**Step 3 — Build the release APK** (unsigned):
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release-unsigned.apk
```

**Step 4 — Build the AAB** (for Google Play):
```bash
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

**Step 5 — Sign for release** (required for Play Store and sideloading on most devices):
```bash
# Generate a keystore (one-time):
keytool -genkey -v -keystore keystore.jks \
  -alias jellyfishgr -keyalg RSA -keysize 2048 -validity 10000

# Sign the APK:
apksigner sign --ks keystore.jks \
  --ks-key-alias jellyfishgr \
  --out app-release-signed.apk \
  app/build/outputs/apk/release/app-release-unsigned.apk

# Or add signing config to app/build.gradle.kts and Gradle signs during build.
```

---

## Publishing to Google Play

1. Update `applicationId` in `app/build.gradle.kts` if needed.
2. Replace the placeholder `ic_launcher` icons in `res/mipmap-*/`.
3. Generate a signed APK: **Build > Generate Signed Bundle/APK**.
4. Upload the `.aab` to the Google Play Console.

---

## Credits

- Species data: BiodiversityGR (Christos Taklis et al.) — CC BY-NC 3.0 GR
- Outbreak platform: Pelagia Logbook 2026, Merman Conservation Expeditions Ltd
- iNaturalist project: [Jellyfish of Greece](https://www.inaturalist.org/projects/jellyfish-of-greece)
- App development: Merman Conservation Tech
