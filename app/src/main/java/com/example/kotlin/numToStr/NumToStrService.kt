package com.example.kotlin.numToStr

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NumToStrService {
    //Базовая часть адреса
    //Создание объекта, при помощи которого будут выполняться запросы
    val api: NumToStrApi
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://htmlweb.ru/json/convert/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NumToStrApi::class.java) //Создание объекта, при помощи которого будут выполняться запросы
        }
}