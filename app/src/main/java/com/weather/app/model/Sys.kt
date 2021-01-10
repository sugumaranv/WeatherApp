package com.weather.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sugumaran V on 09/01/21.
 */
data class Sys (

    @Expose
    @SerializedName("pod")
    val pod:String
    
    )
