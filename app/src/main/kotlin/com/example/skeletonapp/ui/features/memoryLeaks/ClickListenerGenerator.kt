package com.example.skeletonapp.ui.features.memoryLeaks

import android.app.Activity
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

//class ClickListenerGenerator @Inject constructor() {
class ClickListenerGenerator(private var activity: AppCompatActivity?) {

    init {
        activity?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                Log.d("TAG", ">>> DefaultLifecycleObserver onDestroy ")
                activity = null
            }
        })
    }

    fun displayInfoAlert(
        activity: Activity?,
        message: String
    ) {
        activity?.let {
            AlertDialog.Builder(activity).apply {
                setPositiveButton("Ok") { dialog: DialogInterface, which: Int ->
                    dialog.cancel()
                }
                setOnCancelListener {
                }
                setTitle("Dialog")
                setMessage(message)
                setCancelable(true)
                create().apply { show() }
            }
        }
    }

    fun createListener() = object : ItemClickListener {
        override fun onClick(message: String) {
            displayInfoAlert(activity, message)
        }
    }

}