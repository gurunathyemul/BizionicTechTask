package com.example.bizionictechtask.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.bizionictechtask.R
import com.example.bizionictechtask.databinding.ActivityContainerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavController()
        registerListener()
    }

    private fun registerListener() {
        binding.btnPostListWithWM.setOnClickListener{
            navController.navigate(R.id.postFragmentToWorkMFragment)
        }
    }

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.containerNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}