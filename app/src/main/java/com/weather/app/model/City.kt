package com.weather.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sugumaran V on 09/01/21.
 */
data class City (

    @Expose
    @SerializedName("id")
    val id:Int,

    @Expose
    @SerializedName("name")
    val name:String,

    @Expose
    @SerializedName("country")
    val country:String,

    @Expose
    @SerializedName("population")
    val population:Float,

    @Expose
    @SerializedName("timezone")
    val timezone:Float,

    @Expose
    @SerializedName("sunrise")
    val sunrise:Double,

    @Expose
    @SerializedName("sunset")
    val sunset:Double,

    )
