package com.example.jokeintime.data.remote

import com.example.jokeintime.data.model.JokeResponse
import retrofit2.http.GET

interface JokeApi {

    @GET("joke/Any?safe-mode")
    suspend fun getJoke(): JokeResponse

}