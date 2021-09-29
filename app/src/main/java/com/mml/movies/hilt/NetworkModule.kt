package com.mml.movies.hilt

import com.mml.movies.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): ApiService{
        return Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}