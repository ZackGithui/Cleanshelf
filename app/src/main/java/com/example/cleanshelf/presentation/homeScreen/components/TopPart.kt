package com.example.cleanshelf.presentation.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.cleanshelf.R
import com.example.cleanshelf.presentation.authentication.firebaseAuth.AuthViewModel
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun TopPart(navController: NavController) {
    val authViewModel = AuthViewModel()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val fireStore = FirebaseFirestore.getInstance()
    var profilePictureUri by remember { mutableStateOf(user?.photoUrl?.toString() ?: "") }
    LaunchedEffect(user) {
        user?.uid?.let { uid ->
            val doc = fireStore.collection("users").document(uid).get().await()

            profilePictureUri = doc.getString("profilePicture") ?: user.photoUrl.toString() ?: ""


        }

    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(top= 32.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

       if(auth.currentUser?.displayName?.isNotBlank() == true) {
           auth.currentUser?.email.let {
               Text(
                   text = "Welcome, ${it?.substringBefore('@')}",
                   style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                   color = MaterialTheme.colorScheme.onBackground
               )
           }
       }
        else{
           Text(
               text = "Welcome, User!",
               style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
               color = MaterialTheme.colorScheme.onBackground
           )
       }


        /* Image(
             painter = painterResource(id = R.drawable.cleanshelficon),

             contentDescription = stringResource(id = R.string.logo),
             modifier = Modifier.size(110.dp),
             contentScale = ContentScale.Crop,
            //colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)

         )*/
        Image(
            painter = rememberAsyncImagePainter(profilePictureUri),
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable {
                    navController.navigate(AppScreens.SignOut.route)


                }
        )


    }


}


@Preview
@Composable
private fun TopPart1() {
    val navController = rememberNavController()
    TopPart(navController = navController)

}