package com.example.dripdrip.ui.main.viewmodels

import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.example.dripdrip.data.entities.Post
import com.example.dripdrip.data.entities.User
import com.example.dripdrip.other.Event
import com.example.dripdrip.other.Resource
import com.example.dripdrip.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BasePostViewModel(
    private val repository: MainRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
) : ViewModel() {

    private val _likePostStatus = MutableLiveData<Event<Resource<Boolean>>>()
    val likePostStatus: LiveData<Event<Resource<Boolean>>> = _likePostStatus

    private val _deletePostStatus = MutableLiveData<Event<Resource<Post>>>()
    val deletePostStatus: LiveData<Event<Resource<Post>>> = _deletePostStatus

    private val _likedByUsers = MutableLiveData<Event<Resource<List<User>>>>()
    val likedByUsers: LiveData<Event<Resource<List<User>>>> = _likedByUsers


    fun getUsers(uids: List<String>) {
        if(uids.isEmpty()) return
        _likedByUsers.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher){
            val result = repository.getUsers(uids)
            _likedByUsers.postValue(Event(result))
        }
    }

    fun toggleLikeForPost(post:Post) {
        _likePostStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher){
            val result = repository.toggleLikeForPost(post)
            _likePostStatus.postValue(Event(result))
        }
    }

    fun deletePost(post:Post) {
        _deletePostStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.deletePost(post)
            _deletePostStatus.postValue(Event(result))
        }
    }
}