package com.example.myapplication.tasks.data.remote.retrofit

import com.example.myapplication.tasks.data.remote.SessionManager
import com.example.myapplication.tasks.data.remote.api.TaskieApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private const val BASE_URL =
        "https://ada-taskie-backend.osc-fr1.scalingo.io/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)

        .addInterceptor { chain ->

            val requestBuilder = chain.request()
                .newBuilder()

            SessionManager.token?.let { token ->
                requestBuilder.addHeader(
                    "Authorization",
                    "Bearer $token"
                )
            }

            chain.proceed(requestBuilder.build())
        }
        .build()

    val api: TaskieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskieApi::class.java)
    }
}