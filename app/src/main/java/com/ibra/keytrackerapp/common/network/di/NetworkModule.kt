package com.ibra.keytrackerapp.common.network.di

import com.ibra.keytrackerapp.common.network.BASE_URL0
import com.ibra.keytrackerapp.common.network.BASE_URL1
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideRetrofitBuilder(): List<Retrofit> {
        return listOf(
            Retrofit.Builder()
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .baseUrl(BASE_URL0)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build(),

            Retrofit.Builder()
                .client(
                    OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()
                )
                .baseUrl(BASE_URL1)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()
        )
    }
}