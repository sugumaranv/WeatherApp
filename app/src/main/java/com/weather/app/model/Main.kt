package com.weather.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sugumaran V on 09/01/21.
 */
data class Main (

    @Expose
    @SerializedName("temp")
    val temp:Double,

    @Expose
    @SerializedName("feels_like")
    val feels_like:Double,

    @Expose
    @SerializedName("temp_min")
    val temp_min:Double,

    @Expose
    @SerializedName("temp_max")
    val temp_max:Double,

    @Expose
    @SerializedName("pressure")
    val pressure:Double,

    @Expose
    @SerializedName("sea_level")
    val sea_level:Float,

    @Expose
    @SerializedName("grnd_level")
    val grnd_level:Float,

    @Expose
    @SerializedName("humidity")
    val humidity:Float,

    @Expose
    @SerializedName("temp_kf")
    val temp_kf:Float,

    )
