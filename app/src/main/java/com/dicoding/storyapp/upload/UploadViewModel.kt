package com.dicoding.storyapp.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.storyapp.UserModel
import com.dicoding.storyapp.data.UserRepository
import java.io.File


class UploadViewModel(private val repository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getUpload(token: String, file: File, description: String) =
        repository.getUpload(token, file, description)

    fun getUpload(token: String, file: File, description: String, lat: String, lon: String) =
        repository.getUpload(token, file, description,lat,lon)


}