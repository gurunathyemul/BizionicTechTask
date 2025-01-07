package com.example.bizionictechtask.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bizionictechtask.R
import com.example.bizionictechtask.Validation.isValidEmail
import com.example.bizionictechtask.databinding.FragmentLoginBinding
import com.example.bizionictechtask.gone
import com.example.bizionictechtask.showToast
import com.example.bizionictechtask.viewmodel.AuthViewModel
import com.example.bizionictechtask.visible
import com.example.data.model.Resource

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListener()
        observers()
    }

    private fun observers() {
        authViewModel.signInStatus.observe(viewLifecycleOwner) { data: Resource<Boolean> ->
            binding.pbLoader.gone()
            when (data) {
                is Resource.Loading -> {
                    binding.pbLoader.visible()
                }

                is Resource.Success -> {
                    requireActivity().showToast("Login Successful")
                    startActivity(Intent(requireActivity(), ContainerActivity::class.java))
                    requireActivity().finish()
                }

                is Resource.Error -> {
                    requireActivity().showToast(
                        "Login Failed: ${data.message}"
                    )
                }
            }

        }
    }

    private fun registerListener() {
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.loginFragmentToSignUpFragment)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailInput.text.toString()
            val password = binding.etPasswordInput.text.toString()

            if (validateInput(email, password)) {
                authViewModel.loginUser(email, password)
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                requireActivity().showToast("Email cannot be empty")
                false
            }

            !email.isValidEmail() -> {
                requireActivity().showToast("Invalid email format")
                false
            }

            password.isEmpty() -> {
                requireActivity().showToast("Password cannot be empty")
                false
            }

            password.length < 6 -> {
                requireActivity().showToast("Password must be at least 6 characters")
                false
            }

            else -> true
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etEmailInput.setText("")
        binding.etPasswordInput.setText("")
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}