package com.dicoding.storyapp.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.data.Result
import com.dicoding.storyapp.data.UserRepository
import com.dicoding.storyapp.response.StoryResponse
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: UserRepository) :ViewModel() {

    private val _location = MutableLiveData<Result<StoryResponse>>()
    val location: LiveData<Result<StoryResponse>> = _location

    fun getStoriesWithLocation(token: String) {
        viewModelScope.launch {
            repository.getStoriesWithLocation(token).asFlow().collect{
                _location.value = it
            }
        }

    }

    fun getSession() = repository.getSession().asLiveData()
}