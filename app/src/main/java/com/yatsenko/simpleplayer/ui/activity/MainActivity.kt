package com.yatsenko.simpleplayer.ui.activity

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Rational
import com.yatsenko.simpleplayer.R
import com.yatsenko.simpleplayer.ui.fragment.VideoPlayerFragment
import com.yatsenko.simpleplayer.utils.currentNavigationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Picture in Picture related functions.
    override fun onUserLeaveHint() {

        if (supportFragmentManager.currentNavigationFragment !is VideoPlayerFragment)
            return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enterPictureInPictureMode(
                with(PictureInPictureParams.Builder()) {
                    val width = 16
                    val height = 9
                    setAspectRatio(Rational(width, height))
                    build()
                })
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {

        val fragment = supportFragmentManager.currentNavigationFragment
        if (fragment is VideoPlayerFragment) {
            fragment.useController(!isInPictureInPictureMode)
        }

    }

}