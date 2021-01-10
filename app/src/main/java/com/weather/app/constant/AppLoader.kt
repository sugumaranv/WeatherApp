package com.weather.app.constant

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import com.weather.app.R

/**
 * Created by Sugumaran V on 09/01/21.
 */
public class AppLoader(contextConstuctor: Context) {
    //AppLoader class used for loading the progressbar when http request and progressbar will be closed when response arrived.
    private var dialog: Dialog? = null
    private var context: Context? = null

    init {
        context = contextConstuctor
        dialogInitialize()
    }

    //Initialize the dialog
    private fun dialogInitialize() {
        dialog = context?.let { Dialog(it, R.style.Theme_AppCompat_Dialog) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // for showing curved background popup
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.loader)
        val window: Window? = dialog?.getWindow()
        val width = (context?.getResources()?.getDisplayMetrics()?.widthPixels?.times(0.85)) as Double
        window?.setLayout(width.toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        onViewChanged(dialog)
    }

    //Dialog view init
    private fun onViewChanged(dialog: Dialog?){
        dialog?.findViewById<ProgressBar>(R.id.progressBar)
    }

    //Loader visibility handle
    fun loaderVisibility(activity: Activity, isShow: Boolean){

        activity.runOnUiThread(){

            if(isShow){
                dialog?.show()
            }else{
                dialog?.hide()
            }
        }

    }

}