package com.example.cleanshelf

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessaging

class FirebaseMessaging : FirebaseMessagingService(){

    override fun onMessageReceived(message: RemoteMessage) {
       message.notification?.let {
           showNotification(it.title,it.body)
       }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_TOKEN", "New Token: $token")
    }


    private fun showNotification(title:String?,message:String?){
        val channelId ="Push_notification"
        val notificationId = System.currentTimeMillis().toInt()

        val intent = Intent(this,MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //creating notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                "Push Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this,channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.cleanshelficon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()


        notificationManager.notify(notificationId,notification)




    }


}

fun fetchFirebaseToken(){
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task->
        if(task.isSuccessful){
            val token =  task.result
            Log.d("FCM TOKEN", "fetchFirebaseToken: $token")
        }else
        {
            Log.d("FCM TOKEN", "fetchFirebaseToken failed: ", task.exception)
        }

    }
}

