package com.example.bluromatic.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.OUTPUT_PATH
import com.example.bluromatic.R
import com.example.bluromatic.getImageUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

private const val TAG = "CleanupWorker"
class CleanupWorker (context: Context, params: WorkerParameters) : CoroutineWorker(context,params) {
    override suspend fun doWork(): Result {
        makeStatusNotification(
            applicationContext.resources.getString(R.string.cleaning_up_files),
            applicationContext
        )

        return withContext(Dispatchers.IO) {
            delay(DELAY_TIME_MILLIS)

            return@withContext try {
                val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)

                if (outputDirectory.exists()){
                    val entries = outputDirectory.listFiles()
                    if (entries != null) {
                        for (entry in entries){
                            val name = entry.name
                            if (name.isNotEmpty() && name.endsWith(".png")){
                                val deleted = entry.delete()
                                Log.i(TAG, "Deleted $name - $deleted")
                            }
                        }
                    }
                }

                Result.success()
            } catch (e: Exception){
                e.printStackTrace()
                Result.failure()
            }
        }
    }
}