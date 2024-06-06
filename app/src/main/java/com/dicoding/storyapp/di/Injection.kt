package com.dicoding.storyapp.di

import android.content.Context
import com.dicoding.storyapp.UserPreference
import com.dicoding.storyapp.data.UserRepository
import com.dicoding.storyapp.dataStore
import com.dicoding.storyapp.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService, pref)
    }
}