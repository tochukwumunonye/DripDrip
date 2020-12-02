package com.example.dripdrip.ui.main.fragments

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dripdrip.R
import com.example.dripdrip.adapter.PostAdapter
import com.example.dripdrip.databinding.FragmentHomeBinding
import com.example.dripdrip.ui.main.viewmodels.BasePostViewModel
import com.example.dripdrip.ui.main.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BasePostFragment(R.layout.fragment_home) {


    override val basePostViewModel: BasePostViewModel
        get()  {
            val vm: HomeViewModel by viewModels()
            return vm
        }

    private val viewModel: HomeViewModel by lazy { basePostViewModel as HomeViewModel}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.pagingFlow.collect {
                postAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            postAdapter.loadStateFlow.collectLatest {
                allPostsProgressBar?.isVisible = it.refresh is LoadState.Loading ||
                        it.append is LoadState.Loading
            }
        }

    }
    private fun setUpRecyclerView() = rvAllPosts.apply{
        adapter = postAdapter
        layoutManager = LinearLayoutManager(requireContext())
        itemAnimator = null

    }

}