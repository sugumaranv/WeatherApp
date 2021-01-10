package com.weather.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sugumaran V on 09/01/21.
 */

class WeatherInfo(

        @Expose
        @SerializedName("coord")
        val coord: Coord,

        @Expose
        @SerializedName("weather")
        val weatherList: List<WeatherNew>,

        @Expose
        @SerializedName("base")
        val base: String,

        @Expose
        @SerializedName("main")
        val main: Main,

        @Expose
        @SerializedName("wind")
        val wind: Wind,

)