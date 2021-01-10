package com.weather.app.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.weather.app.R
import com.weather.app.constant.AppConstants

/**
 * Created by Sugumaran V on 10/01/21.
 */

/*This MainFragmentHolderActivity is used for manipulating the HomeFragment, CityFragment, and
* HelpFragment.
*
* Based on the interface option, data can be shared among the Fragments.
*
* Initially HomeFrgment will be shown, based on the user choice, user navigate to desired pages.*/
class MainFragmentHolderActivity: AppCompatActivity(), MainFragmentInterface {

    var lat:Double = 0.0
    var lng:Double = 0.0

    lateinit var networkInterface : NetworkInterface
     var isNetworkAvailable : Boolean = false

    public fun setLatitude(lat: Double){
        this.lat = lat
    }

    public fun setLongitude(lng: Double){
        this.lng = lng
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_fragment_holder_activity)

        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            registerNetworkCallback(this)
        }


        if (savedInstanceState == null) {

            val homeFragment = HomeFragment.newInstance(this@MainFragmentHolderActivity, this@MainFragmentHolderActivity)
            networkInterface = homeFragment

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, homeFragment, "HomeFragment")
                    .commit()
        }

    }

    override fun callHomeFragment() {

        val homeFragment = HomeFragment.newInstance(this@MainFragmentHolderActivity, this@MainFragmentHolderActivity)
        networkInterface = homeFragment

        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, homeFragment , "HomeFragment")
                .commit()
    }

    override fun callHelpFragment() {
        val helpFragment = HelpFragment.newInstance(this@MainFragmentHolderActivity, this@MainFragmentHolderActivity)
        networkInterface = helpFragment

        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, helpFragment, "HelpFragment")
                .commit()
    }

    override fun callCityFragment() {

        val cityFragment = CityFragment.newInstance(lat, lng, this@MainFragmentHolderActivity, this@MainFragmentHolderActivity)
        networkInterface = cityFragment

        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,cityFragment , "CityFragment")
                .commit()
    }

    override fun callLocationPickFragment() {
        val intent = Intent(this@MainFragmentHolderActivity, LocationPickActivity::class.java)
        startActivity(intent)
        finish()

    }

    override fun onBackPressed() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, HomeFragment.newInstance(this@MainFragmentHolderActivity, this@MainFragmentHolderActivity), "HomeFragment")
                .commit()
    }


    //Support from Android nougat (Android 7 APi level 24 onwards...
    @RequiresApi(Build.VERSION_CODES.N)
    fun registerNetworkCallback(activity: AppCompatActivity) {
        try {
            val connectivityManager =
                    activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isNetworkAvailable = true
                    networkInterface.isAvailable(true)
                    Toast.makeText(activity,"Network available..", Toast.LENGTH_SHORT).show()
                }

                override fun onLost(network: Network) {
                    isNetworkAvailable = false
                    networkInterface.isAvailable(false)
                    Toast.makeText(activity,"No network available..", Toast.LENGTH_SHORT).show()
                }
            })

        } catch (e: java.lang.Exception) {
            println(e)
        }
    }


    public interface NetworkInterface{
        fun isAvailable(isAvaiable:Boolean)
    }

}