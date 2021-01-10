package com.weather.app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Sugumaran V on 09/01/21.
 */

/*LocationModel class used for saving the bookmark location of the user choices.
It also used as Room database table.*/

@Entity
data class LocationModel (

        @PrimaryKey(autoGenerate = true)
        val idW:Int? = null,

        @ColumnInfo(name = "address")
        val address:String,

        @ColumnInfo(name = "lat")
        val lat:Double,

        @ColumnInfo(name = "lng")
        val lng:Double

        )



