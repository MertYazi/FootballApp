package com.mertyazi.footballapp.shared.data.repository.api

import com.mertyazi.footballapp.BuildConfig
import com.mertyazi.footballapp.Constants.BASE_URL
import com.mertyazi.footballapp.Constants.TOKEN
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        fun getService(): MatchesService {
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor { chain ->
                    val url = chain
                        .request()
                        .url
                        .newBuilder()
                        .build()
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .url(url)
                            .header(TOKEN, BuildConfig.API_KEY)
                            .build()
                    )
                }
                .build()

            val build = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return build.create(MatchesService::class.java)
        }
    }
}