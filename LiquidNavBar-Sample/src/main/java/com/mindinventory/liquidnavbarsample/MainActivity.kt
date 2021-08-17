package com.mindinventory.liquidnavbarsample


import android.os.Bundle
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
    }

    private fun changeFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame1, fragment).commit()
    }

    private fun zoomOut(fragment: Fragment) {
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.expand_out)
        aniSlide.duration = 600
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
    }

    private fun zoomIn() {
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.expand_in)
        aniSlide.duration = 300
        frame1.startAnimation(aniSlide)
    }

    override fun onNavigationItemSelected(indexOfItemSelected: Int): Boolean {
        when (indexOfItemSelected) {
            0 -> {
                if (indexOfItemSelected != selectedItemId) {
                    //zoomOut(FirstFragment())
                    changeFragment(FirstFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
            1 -> {
                if (indexOfItemSelected != selectedItemId) {
//                    zoomOut(SecondFragment())
                    changeFragment(SecondFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
            2 -> {
                if (indexOfItemSelected != selectedItemId) {
//                    zoomOut(ThirdFragment())
                    changeFragment(ThirdFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
            3 -> {
                if (indexOfItemSelected != selectedItemId) {
//                    zoomOut(FourFragment())
                    changeFragment(FourFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
        }
        return true
    }
}
