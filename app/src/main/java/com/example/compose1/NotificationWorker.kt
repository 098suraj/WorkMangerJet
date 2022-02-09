package com.example.compose1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val taskData = inputData
        val taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS)
        showNotification(
            "Suraj Shukla",
            taskDataString.toString()
        )
        val outputData = Data.Builder().putString(WORK_RESULT, "task Finished").build()
        return Result.success(outputData)

    }

    private fun showNotification(name: String, desc: String) {
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId="message_Channel"
        val channelName="message_name"
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel=NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val builder=NotificationCompat.Builder(applicationContext,channelId)
            .setContentText(name)
            .setContentText(desc)
            .setSmallIcon(R.drawable.cheems)
        manager.notify(1,builder.build())
    }

    companion object {
        const val WORK_RESULT = "work_result"
    }
}