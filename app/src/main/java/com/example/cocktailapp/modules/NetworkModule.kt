package com.example.cocktailapp.modules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    private const val TIMEOUT = 30L

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
        )
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
    }.build()

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            explicitNulls = false
        }
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        client(okHttpClient)
        addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    }.build()
}