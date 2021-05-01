package com.example.kotlin.Forecast

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Consumer

object ForecastToString {
    fun getForecast(
        city: String?,
        callback: Consumer<String?>?
    ) {
        val api: ForecastApi = ForecastService.api
        val call: Call<Forecast?>? = api.getCurrentWeather(city)
        call!!.enqueue(object : Callback<Forecast?> {
            override fun onResponse(
                call: Call<Forecast?>?,
                response: Response<Forecast?>?
            ) {
                val result = response!!.body()
                if (result != null) {
                    val answer =
                        "сейчас где-то " + result.current!!.temperature.toString() + "\u00B0С" + " и " + result.current!!.weather_descriptions!![0]
                    callback!!.accept(answer)
                } else {
                    callback!!.accept("Не могу узнать погоду вне dungeon")
                }
            }

            override fun onFailure(
                call: Call<Forecast?>?,
                t: Throwable?
            ) {
                Log.w("WEATHER", t!!.message!!)
            }
        })
    }
}