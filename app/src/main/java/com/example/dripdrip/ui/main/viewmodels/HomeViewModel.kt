package com.example.dripdrip.ui.main.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.dripdrip.data.entities.Post
import com.example.dripdrip.data.pagingSource.FollowPostPagingSource
import com.example.dripdrip.other.Constants.PAGE_SIZE
import com.example.dripdrip.other.Event
import com.example.dripdrip.other.Resource
import com.example.dripdrip.repository.MainRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : BasePostViewModel(repository, dispatcher){

    val pagingFlow = Pager(PagingConfig(PAGE_SIZE)) {
        FollowPostPagingSource(FirebaseFirestore.getInstance())
    }.flow.cachedIn(viewModelScope)










}