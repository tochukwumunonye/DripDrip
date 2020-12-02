package com.example.dripdrip.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dripdrip.R
import com.example.dripdrip.databinding.FragmentLoginBinding
import com.example.dripdrip.other.EventObserver
import com.example.dripdrip.ui.auth.AuthViewModel
import com.example.dripdrip.ui.main.MainActivity
import com.example.dripdrip.ui.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding = FragmentLoginBinding.bind(view)

        subscribeToObservers()

        binding.apply{
            tvRegisterNewAccount.setOnClickListener {
                if (findNavController().previousBackStackEntry != null){
                    findNavController().popBackStack()
                } else findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                )
            }

            tvResetPassword.setOnClickListener {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToResetFragment()
                )
            }

            btnLogin.setOnClickListener {
                viewModel.login(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
        }

    }

    private fun subscribeToObservers() {
        viewModel.loginStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                loginProgressBar.isVisible = false
                snackbar(it)
            },
            onLoading = { loginProgressBar.isVisible = true }
        ){
            loginProgressBar.isVisible = false
            Intent(requireContext(), MainActivity::class.java).also{
                startActivity(it)
                requireActivity().finish()
            }
        })

    }
}