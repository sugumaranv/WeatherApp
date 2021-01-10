package com.weather.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sugumaran V on 09/01/21.
 */
data class Wind (

    @Expose
    @SerializedName("speed")
    val speed:Float,

    @Expose
    @SerializedName("deg")
    val deg:Float

    )
