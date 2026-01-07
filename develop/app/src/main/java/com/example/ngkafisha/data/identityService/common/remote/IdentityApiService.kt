package com.example.ngkafisha.data.identityService.common.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object IdentityApiService {
    private const val BASE_URL = "https://identity.ngkapi.ru/"



    fun create(okHttpClient: OkHttpClient): IdentityApi {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(IdentityApi::class.java)
    }
}