package com.example.kotlin.numToStr

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Consumer

object NumberToString {
    fun getNumber(
        number: String?,
        callback: Consumer<String?>
    ) {
        val api: NumToStrApi = NumToStrService.api
        val call: Call<NumToStr> = api.getNumber(number, "1", "0")
        call.enqueue(object : Callback<NumToStr?> {
            override fun onResponse(
                call: Call<NumToStr?>,
                response: Response<NumToStr?>
            ) {
                val result: NumToStr? = response.body()
                if (result != null) {
                    val answer = "Number is: " + result.number
                    callback.accept(answer)
                } else {
                    callback.accept("Number is invalid!")
                }
            }

            override fun onFailure(
                call: Call<NumToStr?>,
                t: Throwable
            ) {
                Log.w("NUMTOSTR", t.message!!)
            }
        })
    }
}