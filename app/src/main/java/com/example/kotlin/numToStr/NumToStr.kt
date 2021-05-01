package com.example.kotlin.numToStr

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NumToStr {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("str")
    @Expose
    var number: String? = null
}