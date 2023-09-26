package com.example.waterme.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.waterme.R

class WaterReminderWorker (context: Context, parameters: WorkerParameters) : CoroutineWorker(context,parameters) {

    override suspend fun doWork(): Result {
        val plantName = inputData.getString(nameKey)
        makePlantReminderNotification(
            applicationContext.getString(R.string.time_to_water,plantName),
            applicationContext
        )

        return Result.success()
    }

    companion object{
        val nameKey = "NAME"
    }
}