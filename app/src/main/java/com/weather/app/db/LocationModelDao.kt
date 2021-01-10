package com.weather.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by Sugumaran V on 09/01/21.
 */

/*LocationModelDao is a interface used for Room database table data manipulation.*/
@Dao
interface LocationModelDao {
    @Insert
    fun insert(locationModel: LocationModel)

    @Query("DELETE FROM LocationModel WHERE address=:addressVal")
    fun delete(addressVal: String)

    @Query("SELECT * FROM LocationModel")
    fun getAll():List<LocationModel>


}