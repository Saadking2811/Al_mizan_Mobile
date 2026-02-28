package dz.gov.almizan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dz.gov.almizan.ui.screens.DashboardScreen
import dz.gov.almizan.ui.screens.HomeScreen
import dz.gov.almizan.ui.screens.LoginScreen
import dz.gov.almizan.ui.screens.SearchScreen
import dz.gov.almizan.ui.screens.TenderDetailScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Search : Screen("search")
    object Dashboard : Screen("dashboard")
    object TenderDetail : Screen("tender/{tenderId}") {
        fun createRoute(tenderId: String) = "tender/$tenderId"
    }
}

@Composable
fun AlMizanNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onSearchClick = { navController.navigate(Screen.Search.route) },
                onTenderClick = { tenderId ->
                    navController.navigate(Screen.TenderDetail.createRoute(tenderId))
                },
                onDashboardClick = { navController.navigate(Screen.Dashboard.route) }
            )
        }
        
        composable(Screen.Search.route) {
            SearchScreen(
                onTenderClick = { tenderId ->
                    navController.navigate(Screen.TenderDetail.createRoute(tenderId))
                },
                onBackClick = { navController.navigateUp() }
            )
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onBackClick = { navController.navigateUp() },
                onTenderClick = { tenderId ->
                    navController.navigate(Screen.TenderDetail.createRoute(tenderId))
                }
            )
        }
        
        composable(
            route = Screen.TenderDetail.route,
            arguments = listOf(navArgument("tenderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tenderId = backStackEntry.arguments?.getString("tenderId") ?: ""
            TenderDetailScreen(
                tenderId = tenderId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
