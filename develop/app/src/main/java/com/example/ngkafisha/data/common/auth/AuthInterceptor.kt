package com.example.ngkafisha.data.common.auth

import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionInfoStore: SessionInfoStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Если не авторизован — отправить как есть
        if (!sessionInfoStore.isAuth)
            return chain.proceed(request)

        // Добавляем токен
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer ${sessionInfoStore.accessToken}")
            .build()

        return chain.proceed(newRequest)
    }
}