package com.weather.app.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.weather.app.constant.AppConstants
import com.weather.app.R
import com.weather.app.db.WeatherDatabase
import com.weather.app.db.LocationModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/*LocationPickActivity is used for showing the current location of the user and in map center, marker
* will be used to pick the location.
*
* Based on the marker position, location of lat and lng will be get and by using the lat lng,
* location of the address will be findout using the GeoCoder class..
*
* It also has the availability to check the runtime location permission and ask for GPS location
* service to the user when GPS is not enabled.*/
class LocationPickActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var btnAddLocationPoint: Button
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mCenterLatLong: LatLng
    private var isGPSLocationServiceCalled :Boolean = false
    private var isNetworkActive :Boolean = false
    private lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_pick)

        initUI()
        initListener()
    }

    private fun initUI(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map)as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnAddLocationPoint = findViewById(R.id.btnAddLocationPoint)

    }

    private fun initListener(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            registerNetworkCallback(this)
        }

        btnAddLocationPoint.setOnClickListener {

            if(isNetworkActive ){
                mCenterLatLong = map.cameraPosition.target
                println("Marker position is::: $mCenterLatLong")
                getAddressFromLocationPoint(mCenterLatLong)
            }else{
                Toast.makeText(this,"No network available..", Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!

        map.getUiSettings().setZoomControlsEnabled(true)

        initMap()
    }

    private fun initMap(){

        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )!=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                AppConstants.LOCATION_PERMISSION_REQUEST_CODE
            )
        }else if(AppConstants.checkLocationService(this@LocationPickActivity))
            showLocationServicePopup()
        else{
            moveCurrentLocation()
        }
    }


    private fun moveCurrentLocation(){
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if(location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    private fun showLocationServicePopup(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@LocationPickActivity)

        //Setting message manually and performing action on button click
        builder.setMessage(R.string.my_dialog_message)
            .setCancelable(false)
            .setPositiveButton(
                R.string.my_dialog_button_yes,
                DialogInterface.OnClickListener { _, id ->

                    isGPSLocationServiceCalled = true
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                })
            .setNegativeButton(
                R.string.my_dialog_button_no
            ) { dialog, id ->
                dialog.cancel()
            }

        val alert: AlertDialog = builder.create()
        alert.setTitle(R.string.my_dialog_title)
        alert.show()
    }

    override fun onResume() {
        super.onResume()
        if(isGPSLocationServiceCalled){
            isGPSLocationServiceCalled = false
            initMap()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        initMap()
    }

    private fun getAddressFromLocationPoint(location: LatLng){

        val addresses: List<Address>
        val geoCoder = Geocoder(this, Locale.getDefault())

        addresses = geoCoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )


        //addresses object null check handled.
        if(addresses!=null && !addresses.isEmpty()){
            val address: String = addresses[0].getAddressLine(0)

            val db = WeatherDatabase(this@LocationPickActivity)
            GlobalScope.launch {

                val locationModel = LocationModel(address = address, lat = location.latitude, lng = location.longitude)
                db.locationModelDao().insert(locationModel)
            }.invokeOnCompletion {

                val intent = Intent(this@LocationPickActivity, MainFragmentHolderActivity::class.java)
                startActivity(intent)
                finish()
            }
        }



    }

    override fun onBackPressed() {
        //Back press not clickable...
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
                    isNetworkActive = true
                    Toast.makeText(activity,"Network available..", Toast.LENGTH_SHORT).show()
                }

                override fun onLost(network: Network) {
                    isNetworkActive = false
                    Toast.makeText(activity,"No network available..", Toast.LENGTH_SHORT).show()
                }
            })

        } catch (e: java.lang.Exception) {
            println(e)
        }
    }
}