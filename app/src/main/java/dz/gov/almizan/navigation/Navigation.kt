package dz.gov.almizan.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dz.gov.almizan.ui.screens.*
import dz.gov.almizan.ui.theme.AlMizanColors

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Main : Screen("main")
    object TenderDetail : Screen("tender/{tenderId}") {
        fun createRoute(tenderId: String) = "tender/$tenderId"
    }
}

sealed class BottomTab(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home : BottomTab("tab_home", "Accueil", Icons.Filled.Home, Icons.Outlined.Home)
    object Search : BottomTab("tab_search", "Recherche", Icons.Filled.Search, Icons.Outlined.Search)
    object Notifications : BottomTab("tab_notifs", "Alertes", Icons.Filled.Notifications, Icons.Outlined.Notifications)
    object Profile : BottomTab("tab_profile", "Profil", Icons.Filled.Person, Icons.Outlined.Person)
}

val bottomTabs = listOf(BottomTab.Home, BottomTab.Search, BottomTab.Notifications, BottomTab.Profile)

@Composable
fun AlMizanNavigation() {
    val rootNav = rememberNavController()
    NavHost(navController = rootNav, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(onSplashComplete = {
                rootNav.navigate(Screen.Login.route) { popUpTo(Screen.Splash.route) { inclusive = true } }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    rootNav.navigate(Screen.Main.route) { popUpTo(Screen.Login.route) { inclusive = true } }
                },
                onRegisterClick = { rootNav.navigate(Screen.Register.route) },
                onForgotPasswordClick = { rootNav.navigate(Screen.ForgotPassword.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onBackClick = { rootNav.navigateUp() },
                onRegisterSuccess = {
                    rootNav.navigate(Screen.Main.route) { popUpTo(Screen.Login.route) { inclusive = true } }
                }
            )
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(onBackClick = { rootNav.navigateUp() })
        }
        composable(Screen.Main.route) {
            MainScreen(
                onTenderClick = { tenderId -> rootNav.navigate(Screen.TenderDetail.createRoute(tenderId)) },
                onLogout = { rootNav.navigate(Screen.Login.route) { popUpTo(Screen.Main.route) { inclusive = true } } }
            )
        }
        composable(
            route = Screen.TenderDetail.route,
            arguments = listOf(navArgument("tenderId") { type = NavType.StringType })
        ) { backStack ->
            TenderDetailScreen(
                tenderId = backStack.arguments?.getString("tenderId") ?: "",
                onBackClick = { rootNav.navigateUp() }
            )
        }
    }
}

@Composable
fun MainScreen(onTenderClick: (String) -> Unit, onLogout: () -> Unit) {
    val mainNav = rememberNavController()
    val backStackEntry by mainNav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    var unreadCount by remember { mutableIntStateOf(3) }

    Scaffold(
        bottomBar = {
            AlMizanBottomBar(currentRoute = currentRoute, unreadCount = unreadCount) { tab ->
                mainNav.navigate(tab.route) {
                    popUpTo(mainNav.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = mainNav,
            startDestination = BottomTab.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomTab.Home.route) {
                HomeScreen(
                    onSearchClick = {
                        mainNav.navigate(BottomTab.Search.route) {
                            popUpTo(mainNav.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    },
                    onTenderClick = onTenderClick,
                    onDashboardClick = {
                        mainNav.navigate(BottomTab.Profile.route) {
                            popUpTo(mainNav.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    }
                )
            }
            composable(BottomTab.Search.route) {
                SearchScreen(onTenderClick = onTenderClick, onBackClick = { mainNav.navigateUp() })
            }
            composable(BottomTab.Notifications.route) { NotificationsScreen() }
            composable(BottomTab.Profile.route) { ProfileScreen(onLogout = onLogout) }
        }
    }
}

@Composable
fun AlMizanBottomBar(currentRoute: String?, unreadCount: Int, onTabSelected: (BottomTab) -> Unit) {
    NavigationBar(
        containerColor = AlMizanColors.White,
        contentColor = AlMizanColors.NavySovereign,
        tonalElevation = 4.dp
    ) {
        bottomTabs.forEach { tab ->
            val selected = currentRoute == tab.route
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Box {
                        Icon(
                            imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                            contentDescription = tab.label,
                            modifier = Modifier.size(24.dp)
                        )
                        if (tab is BottomTab.Notifications && unreadCount > 0) {
                            Box(
                                modifier = Modifier.size(16.dp).clip(CircleShape)
                                    .background(AlMizanColors.Error).align(Alignment.TopEnd),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(if (unreadCount > 9) "9+" else "$unreadCount", color = AlMizanColors.White, fontSize = 8.sp)
                            }
                        }
                    }
                },
                label = { Text(tab.label, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AlMizanColors.NavySovereign,
                    selectedTextColor = AlMizanColors.NavySovereign,
                    indicatorColor = AlMizanColors.GoldAlMizan.copy(alpha = 0.15f),
                    unselectedIconColor = AlMizanColors.Gray400,
                    unselectedTextColor = AlMizanColors.Gray400
                )
            )
        }
    }
}
