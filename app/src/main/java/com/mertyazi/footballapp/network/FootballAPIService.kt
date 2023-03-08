package com.mertyazi.footballapp.network

import com.mertyazi.footballapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FootballAPIService {
    companion object {
        val api = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballAPI::class.java)
    }
}