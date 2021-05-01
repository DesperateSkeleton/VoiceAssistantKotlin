package com.example.kotlin.numToStr

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NumToStrApi {
    @GET("num2str")
    fun getNumber(
        @Query("num") num: String?,
        @Query("uc") uc: String?,
        @Query("dec") dec: String?
    ): Call<NumToStr>
}