package com.takusemba.jethub.api.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.takusemba.jethub.api.DeveloperApi
import com.takusemba.jethub.api.DeveloperApiClient
import com.takusemba.jethub.api.RepoApi
import com.takusemba.jethub.api.RepoApiClient
import com.takusemba.jethub.api.SearchApi
import com.takusemba.jethub.api.SearchApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @ExperimentalSerializationApi
  @Singleton
  @Provides
  fun provideConverterFactory(): Converter.Factory {
    return Json {
      isLenient = true
      ignoreUnknownKeys = true
    }.asConverterFactory("application/json".toMediaType())
  }

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://api.github.com/")
      .addConverterFactory(converterFactory)
      .build()
  }

  @Singleton
  @Provides
  fun provideRepoApi(retrofit: Retrofit): RepoApi {
    return RepoApiClient(retrofit)
  }

  @Singleton
  @Provides
  fun provideUserApi(retrofit: Retrofit): DeveloperApi {
    return DeveloperApiClient(retrofit)
  }

  @Singleton
  @Provides
  fun provideSearchApi(retrofit: Retrofit): SearchApi {
    return SearchApiClient(retrofit)
  }
}
