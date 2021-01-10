package com.weather.app.model

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sugumaran V on 09/01/21.
 */

class WeatherResObj(

        @Expose
        @SerializedName("cod")
        val cod: String,

        @Expose
        @SerializedName("message")
        val message: Int,

        @Expose
        @SerializedName("cnt")
        val cnt: Int,

        @Expose
        @SerializedName("list")
        val weatherList: List<WeatherResponse>,

        @Expose
        @SerializedName("city")
        val city: City,


        @ColumnInfo(name = "address")
        val address: String

)