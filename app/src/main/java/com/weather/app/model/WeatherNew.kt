package com.weather.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sugumaran V on 09/01/21.
 */
data class WeatherNew (

    @Expose
    @SerializedName("id")
    val id:Int,

    @Expose
    @SerializedName("main")
    val main:String,

    @Expose
    @SerializedName("description")
    val description:String,

    @Expose
    @SerializedName("icon")
    val icon:String

    )
