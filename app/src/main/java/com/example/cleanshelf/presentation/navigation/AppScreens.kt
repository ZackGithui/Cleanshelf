package com.example.cleanshelf.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Signpost
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Details
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreens(val route: String,val icon:ImageVector,val title: String) {
    data object SignUp : AppScreens("SignUp",Icons.Default.Signpost,"")
    data object SignIn : AppScreens("SignIn",Icons.Default.Signpost,"")
    data object ForgetPassword : AppScreens("ForgotPassword",Icons.Default.Signpost,"")
    data object HomeScreen : AppScreens("HomeScreen",Icons.Outlined.Home,"Home")
    data object DetailScreen : AppScreens("DetailScreen/{id}",Icons.Outlined.Details,"") {
        fun createRoute(id: Int) = "DetailScreen/$id"
    }
    data object CartScreen : AppScreens("CartScreen",Icons.Outlined.AddShoppingCart,"Cart")
    data object  SearchScreen : AppScreens("SearchScreen",Icons.Outlined.Search,"Search")
    data object  BookMarks : AppScreens("BookMarks",Icons.Outlined.BookmarkBorder,"Bookmark")
    data object CheckOutScreen : AppScreens("CheckOut",Icons.Default.Close,"checkout")
    data object SignOut : AppScreens("SignOut",Icons.Default.Close,"signOut")
    data object OrderScreen : AppScreens("OrderScreen",Icons.Default.Close,"Order")
    data object DealsScreen : AppScreens("deals_screen",Icons.Default.Close,"Deals")



}