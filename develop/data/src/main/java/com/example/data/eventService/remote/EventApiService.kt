package com.example.data.eventService.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EventApiService {
    private const val BASE_URL = "https://events.ngkapi.ru/"



    fun create(okHttpClient: OkHttpClient): EventApi {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(EventApi::class.java)
    }
}