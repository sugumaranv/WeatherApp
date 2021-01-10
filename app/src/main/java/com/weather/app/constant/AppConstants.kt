package com.weather.app.constant

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weather.app.R


/**
 * Created by Sugumaran V on 09/01/21.
 */
object AppConstants {

    //Application constants can be used here...
    fun checkLocationService(context: Context): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            println(e)
        }
        return !gpsEnabled && !networkEnabled
    }

     const val LOCATION_PERMISSION_REQUEST_CODE = 1
     const val BOOK_MARK_RESULT = 365
     const val API_KEY = "fae7190d7e6433ec3a45285ffcf55c86"

    fun convertStringFormat(dateString: String):String{
        return dateString
    }


    fun showNetworkErrorPopup(activity: Activity){
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        //Setting message manually and performing action on button click
        builder.setMessage(R.string.no_network_message)
            .setCancelable(false)
            .setPositiveButton(
                R.string.my_dialog_button_yes,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
            .setNegativeButton(
                R.string.my_dialog_button_no
            ) { dialog, id ->
                dialog.cancel()
            }

        val alert: AlertDialog = builder.create()
        alert.setTitle(R.string.no_network_title)
        alert.show()
    }


}