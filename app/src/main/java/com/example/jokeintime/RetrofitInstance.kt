package com.example.jokeintime

import com.example.jokeintime.data.remote.JokeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: JokeApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://v2.jokeapi.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JokeApi::class.java)
    }

}