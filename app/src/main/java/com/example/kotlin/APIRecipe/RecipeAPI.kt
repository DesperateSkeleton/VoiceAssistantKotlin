package com.example.kotlin.APIRecipe

import com.example.kotlin.Forecast.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeAPI
{
    @GET("/search?app_id=60a13333&app_key=83218efdeac944ccc091b890485b063c")
    fun getIngredients(@Query("q") ingredient: String?): Call<Recipe?>?
}