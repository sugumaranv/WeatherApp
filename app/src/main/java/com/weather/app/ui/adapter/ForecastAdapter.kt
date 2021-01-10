package com.weather.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weather.app.R
import com.weather.app.model.WeatherResponse

/**
 * Created by Sugumaran V on 09/01/21.
 */

/*ForecastAdapter is used for Five days forecast details can be shown here...*/
open class ForecastAdapter(context: Context, list: MutableList<WeatherResponse>): RecyclerView.Adapter<ForecastAdapter.MyViewHolder>() {

    private var context: Context
    private var list:MutableList<WeatherResponse>

   init{
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder: MyViewHolder
        val inflater = LayoutInflater.from(parent.context)
        viewHolder = getViewHolder(parent, inflater)

        return viewHolder
    }

    private fun getViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): MyViewHolder {
        val viewHolder: MyViewHolder
        val v1: View = inflater.inflate(R.layout.item_forecast, parent, false)
        viewHolder = MyViewHolder(v1)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val temp = list.get(position).main.temp
        val humidity = list.get(position).main.humidity
        val windSpeed = list.get(position).wind.speed
        val windDeg = list.get(position).wind.deg
        val rain = list.get(position).weather.get(0).main

        holder.txtTemp.text = "$temp"
        holder.txtHumidity.text = "$humidity"
        holder.txtWind.text = "Speed: $windSpeed\nWind Angle: $windDeg"
        holder.txtRainChance.text = "$rain"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val txtTemp: TextView = itemView.findViewById(R.id.txtTemp)
        val txtHumidity: TextView = itemView.findViewById(R.id.txtHumidity)
        val txtRainChance: TextView = itemView.findViewById(R.id.txtRainChance)
        val txtWind: TextView = itemView.findViewById(R.id.txtWind)

    }

}