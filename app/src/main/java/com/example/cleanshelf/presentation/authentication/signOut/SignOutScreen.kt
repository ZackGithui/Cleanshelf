package com.example.cleanshelf.presentation.authentication.signOut

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.authentication.firebaseAuth.AuthViewModel
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

@Composable
fun SignOutScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val context = LocalContext.current
    val authViewModel = AuthViewModel()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val fireStore = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)  // Enable local cache
            .build()
    }

    val storage = FirebaseStorage.getInstance()
    var profilePictureUri by remember { mutableStateOf(user?.photoUrl?.toString() ?: "") }

    //Fetch user details from fireStore
    LaunchedEffect(user) {
        user?.uid?.let { uid ->
            val doc = fireStore.collection("users").document(uid).get().await()
            profilePictureUri = doc.getString("profilePicture") ?: user.photoUrl.toString() ?: ""


        }

    }
    //Image Launcher
    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

            uri.let {
                profilePictureUri = it.toString()
                if (it != null) {
                    uploadProfilePicture(it, storage, user, fireStore)
                }
            }


        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            Image(
                painter = rememberAsyncImagePainter(profilePictureUri),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable {
                        imagePicker.launch("image/*")


                    },


                )
            Spacer(modifier = Modifier.width(60.dp))
            Row (verticalAlignment = Alignment.Bottom,
                ){
                Button(onClick = { updateProfile(profilePictureUri, fireStore, user, context) }) {
                    Text("Update")
                }
            }


        }


        Spacer(modifier = Modifier.height(16.dp))


        // Name Field
        auth.currentUser?.displayName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 23.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        //
        auth.currentUser?.email?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 23.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(19.dp))

        // Sign Out Button
        CleanShelfButton(
            onClick = {
                auth.signOut()
                navController.navigate(AppScreens.SignIn.route)
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Sign Out"
        )
    }
}

// Upload Profile Picture to Firebase Storage
private fun uploadProfilePicture(
    uri: Uri,
    storage: FirebaseStorage,
    user: FirebaseUser?,
    firestore: FirebaseFirestore
) {
    user?.uid?.let { uid ->
        val storageRef = storage.reference.child("profile_pictures/$uid.jpg")
        storageRef.putFile(uri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                firestore.collection("users").document(uid)
                    .update("profilePicture", downloadUri.toString())
            }
        }
    }
}

// Update User Profile in Firestore
private fun updateProfile(

    profilePictureUri: String,
    firestore: FirebaseFirestore,
    user: FirebaseUser?,
    context: android.content.Context
) {
    user?.uid?.let { uid ->
        val updates = mapOf("profilePicture" to profilePictureUri)
        firestore.collection("users").document(uid).set(updates).addOnSuccessListener {
            Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
        }
    }


}




