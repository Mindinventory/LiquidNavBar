package com.mindinventory.liquidnavbar.circularreveal

import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.annotation.RequiresApi
import kotlin.math.hypot

fun View.startCircularReveal() {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onLayoutChange(
            v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int,
            oldRight: Int, oldBottom: Int
        ) {
            v.removeOnLayoutChangeListener(this)
            val cx = v.width / 2
            val cy = v.bottom
            val radius = hypot((cx).toDouble(), (cy).toDouble()).toInt()
            ViewAnimationUtils.createCircularReveal(v, cx, cy, 0f, radius.toFloat()).apply {
                interpolator = DecelerateInterpolator(2f)
                duration = 1500
                start()


            }
        }
    })
}

