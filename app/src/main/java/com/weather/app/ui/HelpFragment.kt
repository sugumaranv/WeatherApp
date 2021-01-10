package com.weather.app.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.weather.app.R
import com.weather.app.constant.AppConstants

/**
 * Created by Sugumaran V on 10/01/21.
 */

/*HelpFragment is used for load the URL in webview. URL will explain about the weather api
details.

CoordinatorLayout is used here as the requirement.*/

class HelpFragment(activity: Activity, mainFragmentInterface: MainFragmentInterface): Fragment(), MainFragmentHolderActivity.NetworkInterface{
    private var mainFragmentInterface: MainFragmentInterface
    private var activity : Activity
    private lateinit var webView: WebView

    init {
        this.activity = activity
        this.mainFragmentInterface = mainFragmentInterface
    }

    companion object {

        fun newInstance(activity: Activity, mainFragmentInterface: MainFragmentInterface): HelpFragment {
            return HelpFragment(activity, mainFragmentInterface)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        container?.removeAllViews()
        val view : View = inflater.inflate(R.layout.fragment_help, container, false)
        initUiView(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getActivity()?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                    getActivity()?.onBackPressed()
            }
        })
    }

    private fun initUiView(view: View) {
        webView = view.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        val url:String = getString(R.string.base_help)
        webView.loadUrl(url)
    }

    override fun isAvailable(isAvaiable: Boolean) {
        //Do any code here...

    }

}