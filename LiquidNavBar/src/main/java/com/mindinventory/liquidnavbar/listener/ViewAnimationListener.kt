package com.mindinventory.liquidnavbar.listener

import android.view.animation.Animation
import androidx.fragment.app.Fragment

interface ViewAnimationListener {

    /**
     *
     * Notifies the start of the animation.
     *
     * @param animation The started animation.
     */
    fun onAnimationStart(animation: Animation?)

    /**
     *
     * Notifies the end of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.
     *
     * @param animation The animation which reached its end.
     */
    fun onAnimationEnd(animation: Animation?, fragment: Fragment?)

    /**
     *
     * Notifies the repetition of the animation.
     *
     * @param animation The animation which was repeated.
     */
    fun onAnimationRepeat(animation: Animation?)

}