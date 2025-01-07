package com.example.bizionictechtask

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.data.remote.ApiService
import com.example.data.utils.DataConstants.ERROR_WM
import com.example.data.utils.DataConstants.SUCCESS_WM
import com.google.gson.Gson
import javax.inject.Inject

class FetchDataWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val posts = apiService.fetchPosts()
            Log.d(TAG, "Fetched ${posts.size} posts.")
            val postJson = Gson().toJson(posts)
            val successData = Data.Builder()
                .putString(SUCCESS_WM, postJson)
                .build()
            Result.success(successData)
        } catch (e: Exception) {
            val failureData = Data.Builder()
                .putString(ERROR_WM, e.message)
                .build()
            Log.e(TAG, "Error fetching data: ${e.message}")
            Result.failure(failureData)
        }
    }

    companion object {
        private const val TAG = "FetchDataWorker"
    }
}
