package com.weather.app.constant

import com.weather.app.model.WeatherInfo
import com.weather.app.model.WeatherResObj
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Sugumaran V on 09/01/21.
 */
interface ApiInterface {
    //Get the Five day forecast details
    @GET("/data/2.5/forecast")
    fun getForsecastOuery(@Query("lat")lat:Double, @Query("lon")lon:Double, @Query("appid")appid:String): Call<WeatherResObj>

    //Get the weather infor detail
    @GET("/data/2.5/weather")
    fun getWeatherInfo(@Query("lat")lat:Double, @Query("lon")lon:Double, @Query("appid")appid:String): Call<WeatherInfo>
}