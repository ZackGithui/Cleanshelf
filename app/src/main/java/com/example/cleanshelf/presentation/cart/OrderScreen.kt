package com.example.cleanshelf.presentation.cart

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleanshelf.presentation.homeScreen.components.ErrorScreen
import com.example.cleanshelf.presentation.homeScreen.components.ImageHolder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun OrderScreen(modifier: Modifier = Modifier) {
    val orderViewModel: OrderViewModel = hiltViewModel()
    val orders = orderViewModel.orderState.collectAsState().value

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 90.dp, start = 12.dp, end = 12.dp)
    ) {
        when {
            orders.loading -> {

                item {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 90.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }


            }

            orders.data.isNotEmpty() -> {
                items(orders.data) { data ->

                    val formattedDate = try {
                        LocalDateTime.parse(data.createdAt)
                            .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                    } catch (e: Exception) {
                        data.createdAt // fallback to raw string
                    }

                    Box(
                        modifier = modifier
                            .padding(vertical = 6.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            //horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                ImageHolder(
                                    image = data.image ?: "",
                                    onClick = {},
                                    modifier = Modifier.width(100.dp)
                                )
                                Spacer(modifier = Modifier.width(20.dp))

                                Column(modifier = Modifier.width(100.dp)) {
                                    data.name?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                                            color = MaterialTheme.colorScheme.onBackground,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Price: ${data.price}",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Order date: $formattedDate",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    }
                }
            }

            orders.error.isNotBlank() -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 90.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorScreen()
                    }

                }

            }

            else -> {
                item {
                    Text("No orders found")
                }
            }
        }
    }
}
