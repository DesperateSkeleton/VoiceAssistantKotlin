package com.example.kotlin.APIRecipe

import com.example.kotlin.Forecast.Forecast
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Recipe : Serializable
{
    @SerializedName("hits")
    @Expose
    var courses: List<Dish>? = null

    inner class Dish {
        @SerializedName("recipe")
        @Expose
        var specificRecipe: SpecificRecipe? = null

        inner class SpecificRecipe {
            @SerializedName("ingredientLines")
            @Expose
            var ingredients: List<String>? = null
        }
    }
}