package com.weather.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sugumaran V on 09/01/21.
 */
class WeatherResponse(

    @Expose
    @SerializedName("dt")
    val dt: Float,

    @Expose
    @SerializedName("main")
    val main: Main,

    @Expose
    @SerializedName("weather")
    val weather: List<Weather>,

    @Expose
    @SerializedName("clouds")
    val clouds: Clouds,

    @Expose
    @SerializedName("wind")
    val wind: Wind,

    @Expose
    @SerializedName("visibility")
    val visibility: Float,

    @Expose
    @SerializedName("pop")
    val pop: Float,

    @Expose
    @SerializedName("rain")
    val rain: Rain,

    @Expose
    @SerializedName("sys")
    val sys: Sys,

    @Expose
    @SerializedName("dt_txt")
    val dt_txt: String

        )