package com.example.kotlin.Forecast

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("/current?access_key=479e7c0f418741b1c9002c67091a1729")
    fun getCurrentWeather(@Query("query") city: String?): Call<Forecast?>?
}