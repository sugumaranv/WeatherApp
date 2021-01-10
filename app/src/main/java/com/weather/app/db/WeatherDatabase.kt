package com.weather.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Sugumaran V on 09/01/21.
 */

/*WeatherDatabase act as a Room persistance database for storing the location related data.*/
@Database(entities = arrayOf(LocationModel::class), version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun locationModelDao(): LocationModelDao

    companion object {
        @Volatile private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                WeatherDatabase::class.java, "weathers.db")
                .build()
    }

}