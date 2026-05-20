package com.mermanconservation.jellyfishgr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mermanconservation.jellyfishgr.ui.screens.DetailScreen
import com.mermanconservation.jellyfishgr.ui.screens.OutbreakScreen
import com.mermanconservation.jellyfishgr.ui.screens.SpeciesListScreen
import com.mermanconservation.jellyfishgr.ui.theme.JellyfishGRTheme

sealed class Screen(val route: String) {
    data object SpeciesList : Screen("species_list")
    data object Outbreak : Screen("outbreak")
    data object Detail : Screen("detail/{speciesId}") {
        fun createRoute(id: String) = "detail/$id"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JellyfishGRTheme(darkTheme = isSystemInDarkTheme()) {
                JellyfishApp()
            }
        }
    }
}

@Composable
fun JellyfishApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        Triple(Screen.SpeciesList, Icons.Filled.List, R.string.nav_species),
        Triple(Screen.Outbreak, Icons.Filled.Warning, R.string.nav_outbreak)
    )

    val showBottomBar = currentDestination?.route?.startsWith("detail/") == false

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { (screen, icon, labelRes) ->
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = null) },
                            label = { Text(stringResource(labelRes)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SpeciesList.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.SpeciesList.route) {
                SpeciesListScreen(
                    onSpeciesClick = { speciesId ->
                        navController.navigate(Screen.Detail.createRoute(speciesId))
                    }
                )
            }
            composable(Screen.Outbreak.route) {
                OutbreakScreen()
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val speciesId = backStackEntry.arguments?.getString("speciesId") ?: return@composable
                DetailScreen(
                    speciesId = speciesId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
