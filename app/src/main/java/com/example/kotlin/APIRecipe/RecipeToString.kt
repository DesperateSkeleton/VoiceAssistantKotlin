package com.example.kotlin.APIRecipe

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.kotlin.Forecast.Forecast
import com.example.kotlin.Forecast.ForecastApi
import com.example.kotlin.Forecast.ForecastService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Consumer

object RecipeToString
{
    fun getIngredients(
            ingredient: String?,
            callback: Consumer<String?>?
    ) {
        val api: RecipeAPI = RecipeService.api
        val call: Call<Recipe?>? = api.getIngredients(ingredient)
        call!!.enqueue(object : Callback<Recipe?> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                    call: Call<Recipe?>?,
                    response: Response<Recipe?>?
            ) {
                val result = response!!.body()
                if (result != null) {
                    val answer =
                            "Список Ингридиентов: " + java.lang.String.join(", ",result.courses?.get(0)?.specificRecipe?.ingredients)
                    callback!!.accept(answer)
                } else {
                    callback!!.accept("Нет такого блюда!")
                }
            }

            override fun onFailure(call: Call<Recipe?>, t: Throwable)
            {
                Log.w("RECIPE", t!!.message!!)
            }
        })
    }
}