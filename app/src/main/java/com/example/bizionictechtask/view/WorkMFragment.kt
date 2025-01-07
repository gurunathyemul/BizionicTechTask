package com.example.bizionictechtask.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.bizionictechtask.FetchDataWorker
import com.example.bizionictechtask.adapter.PostAdapter
import com.example.bizionictechtask.adapter.PostWMAdapter
import com.example.bizionictechtask.databinding.FragmentPostBinding
import com.example.bizionictechtask.gone
import com.example.bizionictechtask.viewmodel.PostViewModel
import com.example.data.model.Post
import com.example.data.model.PostList
import com.example.data.utils.DataConstants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WorkMFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private lateinit var adapter: PostWMAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workManagerTask()
        adapter = PostWMAdapter()
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    private fun workManagerTask() {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<FetchDataWorker>(
            15, TimeUnit.MINUTES
        ).build()
        WorkManager.getInstance(requireActivity()).enqueue(periodicWorkRequest)
        WorkManager.getInstance(requireActivity()).getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(requireActivity()) { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            val fetchedData =
                                workInfo.outputData.getString(DataConstants.SUCCESS_WM)
                            val postData = Gson().fromJson(fetchedData, PostList::class.java)
                            Log.d(TAG, "Fetched Data: $fetchedData")
                            adapter.updateData(postData)
                        }

                        WorkInfo.State.FAILED -> {
                            val errorMessage = workInfo.outputData.getString(DataConstants.ERROR_WM)
                            Log.e(TAG, "Error: $errorMessage")
                        }

                        else -> {
                        }
                    }
                }
            }
    }

    companion object {
        private const val TAG = "WorkMFragment"
    }
}
