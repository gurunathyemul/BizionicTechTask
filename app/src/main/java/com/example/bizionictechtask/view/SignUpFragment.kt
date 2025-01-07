package com.example.bizionictechtask.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bizionictechtask.Validation.isValidEmail
import com.example.bizionictechtask.databinding.FragmentSignupBinding
import com.example.bizionictechtask.gone
import com.example.bizionictechtask.showToast
import com.example.bizionictechtask.viewmodel.AuthViewModel
import com.example.bizionictechtask.visible
import com.example.data.model.Resource

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
        observers()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Custom back navigation logic
            findNavController().popBackStack()
        }
    }

    private fun registerListeners() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmailInput.text.toString()
            val password = binding.etPasswordInput.text.toString()

            if (email.isValidEmail() && password.length >= 6) {
                authViewModel.signUpUser(email, password)
            } else {
                requireActivity().showToast("Invalid email or password")
            }
        }

    }

    private fun observers() {
        authViewModel.signUpStatus.observe(viewLifecycleOwner) { data: Resource<Boolean> ->
            binding.pbLoader.gone()
            when (data) {
                is Resource.Loading -> {
                    binding.pbLoader.visible()
                }

                is Resource.Success -> {
                    requireActivity().showToast("Registration Successful")
                    findNavController().popBackStack()
                }

                is Resource.Error -> {
                    requireActivity().showToast("Registration Failed ${data.message}")
                }
            }

        }
    }


    companion object {
        private const val TAG = "SignUpFragment"
    }
}