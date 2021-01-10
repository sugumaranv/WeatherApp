package com.weather.app.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.app.R
import com.weather.app.constant.ApiClient
import com.weather.app.constant.AppConstants
import com.weather.app.constant.AppLoader
import com.weather.app.model.*
import com.weather.app.ui.adapter.ForecastAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Sugumaran V on 10/01/21.
 */

/*CityFragment is used for showing the weather details of the perticular location and
* also show the Five days forecast details.
*/
class CityFragment(lat: Double, lng: Double, activity: Activity, mainFragmentInterface: MainFragmentInterface): Fragment(), MainFragmentHolderActivity.NetworkInterface {


    private var mainFragmentInterface: MainFragmentInterface
    private var activity : Activity
    private var isWsApiCalled = false

    lateinit var appLoader: AppLoader

    lateinit var txtTemp: TextView
    lateinit var txtHumidity: TextView
    lateinit var txtRainChance: TextView
    lateinit var txtWind: TextView
    private var lat:Double = 0.0
    private var lng:Double = 0.0

    private lateinit var forecaseRecyclerView: RecyclerView


    init {
        this.lat = lat
        this.lng = lng
        this.activity = activity
        this.mainFragmentInterface = mainFragmentInterface
    }

    companion object {

        fun newInstance(lat: Double, lng: Double, activity: Activity, mainFragmentInterface: MainFragmentInterface): CityFragment {
            return CityFragment(lat, lng, activity, mainFragmentInterface)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {


        container?.removeAllViews()

        val view : View = inflater.inflate(R.layout.fragment_city, container, false)
        initUiView(view)
        return view
    }

    private fun initUiView(view: View) {
        forecaseRecyclerView = view.findViewById<RecyclerView>(R.id.forecaseRecyclerView)
        txtTemp = view.findViewById<TextView>(R.id.txtTemp)
        txtHumidity = view.findViewById<TextView>(R.id.txtHumidity)
        txtRainChance = view.findViewById<TextView>(R.id.txtRainChance)
        txtWind = view.findViewById<TextView>(R.id.txtWind)

        appLoader = AppLoader(activity)

        println("CityActivity lat   $lat and lng   $lng")


        val activityNew: MainFragmentHolderActivity? = getActivity() as MainFragmentHolderActivity?
        if(activityNew?.isNetworkAvailable == true){
            getWeatherInfo()
            getFiveDayForecase()
        }else{
            isWsApiCalled = false
            Toast.makeText(activity,"No network available..", Toast.LENGTH_SHORT).show()
        }

    }


    private fun showDetails(weatherInfo: WeatherInfo){
        activity.runOnUiThread(){
            if(weatherInfo!=null){
                val temp = weatherInfo.main.temp
                val humidity = weatherInfo.main.humidity
                val windSpeed = weatherInfo.wind.speed
                val windDeg = weatherInfo.wind.deg
                val rain = weatherInfo.weatherList.get(0).main
                val des = weatherInfo.weatherList.get(0).description
                txtTemp.text = "$temp"
                txtHumidity.text = "$humidity"
                txtWind.text = "Speed: $windSpeed\nWind Angle: $windDeg"
                txtRainChance.text = "Weather: $rain\nDescription:$des"

            }
        }

    }

    private fun getWeatherInfo(){
        appLoader.loaderVisibility(activity, true)
        val call: Call<WeatherInfo> = ApiClient.getClient.getWeatherInfo(
                lat,
                lng,
                AppConstants.API_KEY
        )

        call.enqueue(object : Callback<WeatherInfo> {
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                appLoader.loaderVisibility(activity, false)
                println()

                response.body()?.let { showDetails(it) }
            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                appLoader.loaderVisibility(activity, false)
                println()
            }
        })
    }

    private fun getFiveDayForecase(){

        val callForecast: Call<WeatherResObj> = ApiClient.getClient.getForsecastOuery(
                lat,
                lng,
                AppConstants.API_KEY
        )

        callForecast.enqueue(object : Callback<WeatherResObj> {
            override fun onResponse(call: Call<WeatherResObj>, response: Response<WeatherResObj>) {
                appLoader.loaderVisibility(activity, false)
                println()
                isWsApiCalled = true
                showForecastDetails(response.body())

            }

            override fun onFailure(call: Call<WeatherResObj>, t: Throwable) {
                appLoader.loaderVisibility(activity, false)
                println()
            }
        })
    }

    private fun showForecastDetails(body: WeatherResObj?) {

        activity.runOnUiThread(){
            var list = mutableListOf<WeatherResponse>()

            if(body!=null){
                list = body.weatherList as MutableList<WeatherResponse>

                if(list!=null){

                    var linearLayoutManager = LinearLayoutManager(activity)
                    forecaseRecyclerView.setLayoutManager(linearLayoutManager)
                    linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    forecaseRecyclerView.setLayoutManager(linearLayoutManager)
                    forecaseRecyclerView.setItemAnimator(DefaultItemAnimator())

                    val adapter = ForecastAdapter(activity, list)
                    forecaseRecyclerView.adapter = adapter


                }
            }
        }

    }

    override fun isAvailable(isAvaiable: Boolean) {
        if(isAvaiable){
            isWsApiCalled = false
            getWeatherInfo()
            getFiveDayForecase()
        }
    }

}