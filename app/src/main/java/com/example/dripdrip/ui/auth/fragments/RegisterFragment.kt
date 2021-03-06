package com.example.dripdrip.ui.auth.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dripdrip.R
import com.example.dripdrip.databinding.FragmentRegisterBinding
import com.example.dripdrip.other.EventObserver
import com.example.dripdrip.ui.auth.AuthViewModel
import com.example.dripdrip.ui.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding = FragmentRegisterBinding.bind(view)

        subscribeToObservers()


        binding.apply {
            tvLogin.setOnClickListener {
                if (findNavController().previousBackStackEntry != null) {
                    findNavController().popBackStack()
                } else findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                )
            }

            btnRegister.setOnClickListener {
                viewModel.register(
                    etEmail.text.toString(),
                    etUsername.text.toString(),
                    etPassword.text.toString(),
                    etRepeatPassword.text.toString(),
                )
            }
        }

    }

    private fun subscribeToObservers() {
        viewModel.registerStatus.observe(viewLifecycleOwner, EventObserver(
            onError = { binding.registerProgressBar.isVisible = false
                snackbar(it)
            },

            onLoading = { binding.registerProgressBar.isVisible = true }
        ) {
            binding.registerProgressBar.isVisible = false
            snackbar(getString(R.string.success_registration))
        })
    }

}
