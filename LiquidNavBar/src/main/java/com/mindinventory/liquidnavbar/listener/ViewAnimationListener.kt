package com.mindinventory.liquidnavbar.listener

import android.view.animation.Animation
import androidx.fragment.app.Fragment

interface ViewAnimationListener {

    fun onAnimationStart(animation: Animation?)
    fun onAnimationEnd(animation: Animation?, fragment: Fragment?)
    fun onAnimationRepeat(animation: Animation?)

}