package com.yatsenko.simpleplayer.utils

import android.util.Log

class Logger {

    companion object {

        fun log(text: String?) {
            Log.i("SimplePlayer", text + "")
        }

    }

}