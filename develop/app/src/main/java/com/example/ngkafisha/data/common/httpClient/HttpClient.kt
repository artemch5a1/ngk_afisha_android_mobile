package com.example.ngkafisha.data.common.httpClient

import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.ngkafisha.data.common.auth.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object HttpClient {

    fun create(sessionInfoStore: SessionInfoStore): OkHttpClient
    {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(AuthInterceptor(sessionInfoStore))
            .build()

        return okHttpClient
    }

}