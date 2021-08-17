package com.mindinventory.liquidnavbarsample


import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mindinventory.liquidnavbar.LiquidNavBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LiquidNavBar.OnNavigationItemSelectListener {

    private var selectedItemId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView.setNavigationListener(this)
        changeFragment(FirstFragment())

        // set a global layout listener which will be called when the layout pass is completed and the view is drawn
        // set a global layout listener which will be called when the layout pass is completed and the view is drawn
        clMain.viewTreeObserver.addOnGlobalLayoutListener(
            object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    //Remove the listener before proceeding
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        clMain.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    } else {
                        clMain.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }
//                    val location = IntArray(2)
//                    ivNotification.getLocationInWindow(location)
//                    val x = location[0]
//                    val y = location[1]
//
//                    Log.e("MainActivity", "X:- $x, Y:- $y")
                    // measure your views here
                }
            }
        )


    }

    private fun changeFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame1, fragment).commit()
    }

    private fun zoomOut(fragment: Fragment) {
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.expand_out)

        aniSlide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                changeFragment(fragment)
                zoomIn()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        frame1.startAnimation(aniSlide)
        bottomNavigationView.startAnimation(aniSlide)
    }

    private fun zoomIn() {
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.expand_in)

        frame1.startAnimation(aniSlide)
        bottomNavigationView.startAnimation(aniSlide)

    }

    override fun onNavigationItemSelected(indexOfItemSelected: Int): Boolean {
        when (indexOfItemSelected) {
            0 -> {
                if (indexOfItemSelected != selectedItemId) {

                    zoomOut(FirstFragment())
//                    changeFragment(FirstFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
            1 -> {
                if (indexOfItemSelected != selectedItemId) {
                    zoomOut(SecondFragment())
//                    changeFragment(SecondFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
            2 -> {
                if (indexOfItemSelected != selectedItemId) {
                    zoomOut(ThirdFragment())
//                    changeFragment(ThirdFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
            3 -> {
                if (indexOfItemSelected != selectedItemId) {
                    zoomOut(FourFragment())
//                    changeFragment(FourFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
        }
        return true
    }
}
