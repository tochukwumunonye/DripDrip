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
import com.example.dripdrip.databinding.FragmentResetBinding
import com.example.dripdrip.other.EventObserver
import com.example.dripdrip.ui.auth.AuthViewModel
import com.example.dripdrip.ui.main.MainActivity
import com.example.dripdrip.ui.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_reset.*

@AndroidEntryPoint
class ResetFragment : Fragment(R.layout.fragment_reset) {

    private lateinit var binding: FragmentResetBinding
    private lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding = FragmentResetBinding.bind(view)

        subscribeToObservers()

        binding.apply {
            backtoLogin.setOnClickListener {
                findNavController().navigate(
                    ResetFragmentDirections.actionResetFragmentToLoginFragment()
                )
            }

            btnReset.setOnClickListener {
                viewModel.reset(
                    resetEmail.text.toString()
                )
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.resetStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                resetProgressBar.isVisible = false
                snackbar(it)
            },
            onLoading = { resetProgressBar.isVisible = true }
        ){
            resetProgressBar.isVisible = false
        })
    }
}