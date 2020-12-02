package com.example.dripdrip.ui.main.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dripdrip.adapter.UserAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LikedByDialog(
    private val userAdapter: UserAdapter
): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val rvLikedBy = RecyclerView(requireContext()).apply{
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Liked By")
            .setView(rvLikedBy)
            .create()
    }
}