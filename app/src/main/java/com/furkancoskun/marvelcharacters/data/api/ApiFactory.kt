package com.furkancoskun.marvelcharacters.data.api

import com.furkancoskun.marvelcharacters.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private fun getInterceptor(): Interceptor {

        return Interceptor { chain ->
            val newUrl = chain.request().url()
                .newBuilder()
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .addHeader("Content-Type", "application/json")
                .build()

            chain.proceed(newRequest)
        }
    }

    private fun getRetrofitClient(): OkHttpClient {

        return OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(getInterceptor())
            .build()
    }

    private fun getRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .client(getRetrofitClient())
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    // Services
    val characterService: CharacterService =
        getRetrofit(Constants.BASE_URL).create(CharacterService::class.java)
}
