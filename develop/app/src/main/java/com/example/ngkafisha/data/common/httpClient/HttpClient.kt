package com.example.ngkafisha.data.common.httpClient

import com.example.ngkafisha.data.common.auth.AuthInterceptor
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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