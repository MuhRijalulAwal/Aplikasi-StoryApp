package com.dicoding.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.dicoding.storyapp.UserModel
import com.dicoding.storyapp.data.UserRepository
import com.dicoding.storyapp.response.Story
import kotlinx.coroutines.launch


class MainViewModel(private val repository: UserRepository) : ViewModel() {
    val listUser = MutableLiveData<ArrayList<Story>>()


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun story(token: String) :LiveData<PagingData<Story>> = repository.getPaging(token)

}