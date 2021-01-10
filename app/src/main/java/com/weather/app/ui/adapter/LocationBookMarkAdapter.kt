package com.weather.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weather.app.R
import com.weather.app.db.LocationModel

/**
 * Created by Sugumaran V on 09/01/21.
 */

/*LocationBookMarkAdapter is used for show the details about the user bookmarked location.*/
open class LocationBookMarkAdapter(context: Context, list: MutableList<LocationModel>, adapterInterface: AdapterInterface): RecyclerView.Adapter<LocationBookMarkAdapter.MyViewHolder>() {

    private var adapterInterface: AdapterInterface;
    private var context: Context
    private var list:MutableList<LocationModel>

   init{
        this.context = context
        this.list = list
        this.adapterInterface = adapterInterface
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
        val v1: View = inflater.inflate(R.layout.item_bookmark, parent, false)
        viewHolder = MyViewHolder(v1)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val address:String = list.get(position).address
        val lat:Double = list.get(position).lat
        val lng:Double = list.get(position).lng


        holder.txtAddress.text = address
        holder.txtLatLng.text = "Position( $lat, $lng )"

        holder.imgRemove.setOnClickListener {
            //Remove the list element here...
            adapterInterface.deletePos(position, address)

        }

        holder.topLayout.setOnClickListener {
            //Show the details here...
            adapterInterface.showDetails(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val topLayout: RelativeLayout = itemView.findViewById(R.id.topLayout)
        val txtAddress: TextView = itemView.findViewById(R.id.txtAddress)
        val txtLatLng: TextView = itemView.findViewById(R.id.txtLatLng)
        val imgRemove: ImageView = itemView.findViewById(R.id.imgRemove)

    }

    public interface AdapterInterface{
        fun deletePos(pos: Int, address: String)
        fun showDetails(position: Int)
    }

    public fun accessList() = list

}