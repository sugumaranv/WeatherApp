package com.weather.app.ui

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.app.R
import com.weather.app.db.WeatherDatabase
import com.weather.app.constant.AppLoader
import com.weather.app.db.LocationModel
import com.weather.app.ui.adapter.LocationBookMarkAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Sugumaran V on 10/01/21.
 */

/*HomeFragment is used for showing the bookmarked locations list and also user can able to
* search the bookmarked location, it will show the list based on the user query.*/
class HomeFragment(activity: Activity, mainFragmentInterface: MainFragmentInterface): Fragment(), LocationBookMarkAdapter.AdapterInterface, MainFragmentHolderActivity.NetworkInterface {
    private var mainFragmentInterface: MainFragmentInterface
    private lateinit var bookmarkLocationRecyclerView: RecyclerView
    private lateinit var btnPickLocation: Button
    private lateinit var btnHelp: Button
    private lateinit var edtTextLocationSearch: EditText
    private lateinit var appLoader: AppLoader
    private lateinit var adapter: LocationBookMarkAdapter
    private lateinit var list : MutableList<LocationModel>
    private lateinit var tempList : MutableList<LocationModel>
    private var activity : Activity

    private var isShowingTempList = false

    init {
        this.activity = activity
        this.mainFragmentInterface = mainFragmentInterface
    }

    companion object {

        fun newInstance(activity: Activity, mainFragmentInterface: MainFragmentInterface): HomeFragment {
            return HomeFragment(activity, mainFragmentInterface)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        container?.removeAllViews()
        val view : View = inflater.inflate(R.layout.home_fragment, container, false)
        initUiView(view)
        return view
    }


    private fun initUiView(view: View) {

        appLoader = AppLoader(activity)

        btnPickLocation = view.findViewById(R.id.btnPickLocation)
        btnHelp = view.findViewById(R.id.btnHelp)
        bookmarkLocationRecyclerView = view.findViewById(R.id.bookmarkLocationRecyclerView)
        edtTextLocationSearch = view.findViewById(R.id.edtTextLocationSearch)

        initListener()
        getDatabaseList()
    }

    private fun initListener(){
        btnPickLocation.setOnClickListener {
            mainFragmentInterface.callLocationPickFragment()

        }

        btnHelp.setOnClickListener {
            mainFragmentInterface.callHelpFragment()
        }

        edtTextLocationSearch.addTextChangedListener(object : TextWatcher {
            private var previousSymbolsCount = 0
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                previousSymbolsCount = s.length
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //Not Implemented
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) {
                    setUpList()
                } else {
                    onEnterValue(s.toString())
                }
            }
        })
    }

    private fun onEnterValue(query: String){

        if(query!=null && list!=null){

            tempList = mutableListOf()

            for (locationModel in list) {
                if(locationModel.address.toLowerCase().contains(query.toLowerCase())){
                    tempList.add(locationModel)
                }
            }
            setUpSearchList(tempList)
        }

    }

    private fun setUpSearchList(tempList: MutableList<LocationModel>) {

        var linearLayoutManager = LinearLayoutManager(activity)
        bookmarkLocationRecyclerView.setLayoutManager(linearLayoutManager)
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        bookmarkLocationRecyclerView.setLayoutManager(linearLayoutManager)
        bookmarkLocationRecyclerView.setItemAnimator(DefaultItemAnimator())

        if(tempList!=null) {
            isShowingTempList = true;
            adapter = LocationBookMarkAdapter(activity, tempList, this)
            bookmarkLocationRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }

    private fun getDatabaseList(){
        val db = WeatherDatabase(activity)

        GlobalScope.launch {
            list = db.locationModelDao().getAll() as ArrayList<LocationModel>
        }.invokeOnCompletion {
            activity.runOnUiThread(){
                setUpList()
            }
        }
    }

    private fun setUpList(){
        var linearLayoutManager = LinearLayoutManager(activity)
        bookmarkLocationRecyclerView.setLayoutManager(linearLayoutManager)
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        bookmarkLocationRecyclerView.setLayoutManager(linearLayoutManager)
        bookmarkLocationRecyclerView.setItemAnimator(DefaultItemAnimator())

        if(list!=null) {
            adapter = LocationBookMarkAdapter(activity, list, this@HomeFragment)
            bookmarkLocationRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun deletePos(pos: Int, address: String) {
        activity.runOnUiThread() {
            appLoader.loaderVisibility(activity, true)
        }

        GlobalScope.launch {
            delay(2000)
            val db = WeatherDatabase(activity)

            db.locationModelDao().delete(address)

        }.invokeOnCompletion {
            activity.runOnUiThread(){

                if(isShowingTempList){
                    if(tempList!=null && pos<tempList.size){
                        tempList.removeAt(pos)
                        adapter.notifyDataSetChanged()
                    }
                }else{
                    if(list!=null && pos<list.size){
                        list.removeAt(pos)
                        adapter.notifyDataSetChanged()
                    }
                }
                appLoader.loaderVisibility(activity, false)
            }
        }

    }

    override fun showDetails(position: Int) {
        var locationModel: LocationModel
        if(isShowingTempList){
            locationModel = tempList.get(position)
        }else{
            locationModel = list.get(position)
        }
        activity.runOnUiThread(){
            (getActivity() as MainFragmentHolderActivity?)?.setLatitude(locationModel.lat)
            (getActivity() as MainFragmentHolderActivity?)?.setLongitude(locationModel.lng)
            mainFragmentInterface.callCityFragment()

        }

    }

    override fun isAvailable(isAvaiable: Boolean) {
        //Do any code here.
    }

}