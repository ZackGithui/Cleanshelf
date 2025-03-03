package com.example.cleanshelf.presentation.homeScreen.components

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

@Composable
fun CategoryPager() {

    val categories = listOf("All","Bakery","Cleaning","Frozen food", "Dairy products","Fresh products","Pantry staples")
    val pagerState = rememberPagerState(pageCount = {categories.size})
    val coroutineScope = rememberCoroutineScope()


    
}