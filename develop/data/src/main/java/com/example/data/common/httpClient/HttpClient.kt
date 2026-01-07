package com.example.data.common.httpClient

import com.example.data.common.auth.AuthInterceptor
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
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