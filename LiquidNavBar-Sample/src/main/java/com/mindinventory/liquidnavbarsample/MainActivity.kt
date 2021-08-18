package com.mindinventory.liquidnavbarsample


import android.os.Bundle
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mindinventory.liquidnavbar.listener.ViewAnimationListener
import com.mindinventory.liquidnavbar.ui.LiquidNavBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LiquidNavBar.OnNavigationItemSelectListener {

    private var selectedItemId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView.setAnimationListener(container, object : ViewAnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?, fragment: Fragment?) {
                fragment?.let { changeFragment(it) }
            }


            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        bottomNavigationView.setNavigationListener(this)
        changeFragment(FeedFragment())
    }

    private fun changeFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
    }

    override fun onNavigationItemSelected(indexOfItemSelected: Int): Boolean {
        when (indexOfItemSelected) {
            0 -> {
                if (indexOfItemSelected != selectedItemId) {
                    bottomNavigationView.zoomOut(FeedFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
            1 -> {
                if (indexOfItemSelected != selectedItemId) {
                    bottomNavigationView.zoomOut(GalleryFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
            2 -> {
                if (indexOfItemSelected != selectedItemId) {
                    bottomNavigationView.zoomOut(FavoriteFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
            3 -> {
                if (indexOfItemSelected != selectedItemId) {
                    bottomNavigationView.zoomOut(SettingFragment())
                    selectedItemId = indexOfItemSelected
                }
            }
        }
        return true
    }
}
