package com.example.ngkafisha.di

import com.example.ngkafisha.data.common.httpClient.HttpClient
import com.example.ngkafisha.data.eventService.remote.EventApi
import com.example.ngkafisha.data.eventService.remote.EventApiService
import com.example.ngkafisha.data.identityService.common.remote.IdentityApi
import com.example.ngkafisha.data.identityService.common.remote.IdentityApiService
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("uploadClient")
    fun provideUploadOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(client: OkHttpClient): IdentityApi {
        return IdentityApiService.create(client)
    }

    @Provides
    @Singleton
    fun provideEventApi(client: OkHttpClient): EventApi {
        return EventApiService.create(client)
    }

    @Provides
    @Singleton
    fun provideHttpClient(sessionInfoStore: SessionInfoStore): OkHttpClient {
        return HttpClient.create(sessionInfoStore)
    }
}