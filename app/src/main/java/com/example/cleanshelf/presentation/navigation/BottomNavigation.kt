package com.example.cleanshelf.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        AppScreens.HomeScreen to Icons.Filled.Home,
        AppScreens.SearchScreen to Icons.Filled.Search,
        AppScreens.CartScreen to Icons.Filled.AddShoppingCart,
        AppScreens.BookMarks to Icons.Filled.Bookmark
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomAppBar(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)) {
        items.forEach { (screen, icon) ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = icon, contentDescription = screen.title) },
               label = { Text(text = screen.title) }
            )
        }
    }
}
