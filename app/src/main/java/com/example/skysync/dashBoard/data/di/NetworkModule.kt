package com.example.skysync.dashBoard.data.di

import com.example.skysync.dashBoard.data.remote.WeatherDataApi
import com.example.skysync.dashBoard.data.repository.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val baseUrl =
        "https://api.open-meteo.com/v1/"

    @Singleton
    @Provides
    fun provideRetrofit(): WeatherDataApi {
        val networkJson = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(baseUrl).callFactory(
                OkHttpClient
                    .Builder()
                    .addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    ).build()
            ).addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            ).build().create(WeatherDataApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherData: WeatherDataApi
    ) = WeatherRepo(weatherData)

}
