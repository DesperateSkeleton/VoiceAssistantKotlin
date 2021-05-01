package com.example.kotlin.APIRecipe

import com.example.kotlin.Forecast.ForecastApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecipeService
{
    val api: RecipeAPI
        get() {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.edamam.com") //Базовая часть адреса
                    .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                    .build()
            return retrofit.create(RecipeAPI::class.java) //Создание объекта, при помощи которого будут выполняться запросы
        }
}