package com.mindinventory.liquidnavbar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.mindinventory.liquidnavbar.R
import com.mindinventory.liquidnavbar.databinding.CustomBottomNavigationViewBinding
import com.mindinventory.liquidnavbar.listener.AnimationListener
import com.mindinventory.liquidnavbar.listener.ViewAnimationListener

@SuppressLint("CustomViewStyleable")
class LiquidNavBar(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs),
    AnimationListener {

    private var binding: CustomBottomNavigationViewBinding? = null
    private var onNavigationItemSelectListener: OnNavigationItemSelectListener? = null
    private var viewAnimationListener: ViewAnimationListener? = null
    private var view: View? = null

    init {
        binding =
            CustomBottomNavigationViewBinding.inflate(LayoutInflater.from(context), this, true)
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.CustomBottomNavigationView)
        //setting background color for LiquidNavBar
        binding!!.bottomNavigationView.backgroundTintList =
            attributes.getColorStateList(R.styleable.CustomBottomNavigationView_backgroundTintNavigation)
        //setting item icon color for
        binding!!.bottomNavigationView.itemIconTintList =
            binding!!.bottomNavigationView.backgroundTintList

        binding!!.bottomNavigationView.inflateMenu(
            attributes.getResourceId(
                R.styleable.CustomBottomNavigationView_menu,
                0
            )
        )
        binding!!.bottomNavigationView.animationListener = this
        attributes.recycle()
        menuItem()

    }

    fun setAnimationListener(view: View?, viewAnimationListener: ViewAnimationListener?) {
        this.view = view
        this.viewAnimationListener = viewAnimationListener
    }

    fun zoomOut(fragment: Fragment) {
        zoomOutTab()

        val aniSlide: Animation =
            AnimationUtils.loadAnimation(context, R.anim.expand_out)

        aniSlide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                viewAnimationListener?.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animation?) {
                viewAnimationListener?.onAnimationEnd(animation, fragment)
                zoomIn()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        view?.startAnimation(aniSlide)
    }

    private fun zoomOutTab() {
        val aniSlideTab: Animation =
            AnimationUtils.loadAnimation(context, R.anim.expand_out_tab)

        aniSlideTab.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                zoomInTab()
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        this.startAnimation(aniSlideTab)
    }

    private fun zoomIn() {
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(context, R.anim.expand_in)
        view?.startAnimation(aniSlide)
    }

    private fun zoomInTab() {
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(context, R.anim.expand_in_tab)
        this.startAnimation(aniSlide)
    }

    //navigation listener for LiquidNavBar
    fun setNavigationListener(onNavigationItemSelectListener: OnNavigationItemSelectListener) {
        this.onNavigationItemSelectListener = onNavigationItemSelectListener
    }

    // adding dynamic menu items into LiquidNavBar.
    private fun menuItem() {
        binding!!.bottomNavigationView.let {
            when (it.menu.size()) {
                2 -> {
                    binding!!.icon1.setImageDrawable(it.menu.getItem(0).icon)
                    binding!!.icon2.setImageDrawable(it.menu.getItem(1).icon)
                    binding!!.icon1.visibility = View.VISIBLE
                    binding!!.icon2.visibility = View.VISIBLE

                }
                3 -> {
                    binding!!.icon1.setImageDrawable(it.menu.getItem(0).icon)
                    binding!!.icon2.setImageDrawable(it.menu.getItem(1).icon)
                    binding!!.icon3.setImageDrawable(it.menu.getItem(2).icon)
                    binding!!.icon1.visibility = View.VISIBLE
                    binding!!.icon2.visibility = View.VISIBLE
                    binding!!.icon3.visibility = View.VISIBLE
                }
                4 -> {
                    binding!!.icon1.setImageDrawable(it.menu.getItem(0).icon)
                    binding!!.icon2.setImageDrawable(it.menu.getItem(1).icon)
                    binding!!.icon3.setImageDrawable(it.menu.getItem(2).icon)
                    binding!!.icon4.setImageDrawable(it.menu.getItem(3).icon)
                    binding!!.icon1.visibility = View.VISIBLE
                    binding!!.icon2.visibility = View.VISIBLE
                    binding!!.icon3.visibility = View.VISIBLE
                    binding!!.icon4.visibility = View.VISIBLE
                }
                5 -> {
                    binding!!.icon1.setImageDrawable(it.menu.getItem(0).icon)
                    binding!!.icon2.setImageDrawable(it.menu.getItem(1).icon)
                    binding!!.icon3.setImageDrawable(it.menu.getItem(2).icon)
                    binding!!.icon4.setImageDrawable(it.menu.getItem(3).icon)
                    binding!!.icon5.setImageDrawable(it.menu.getItem(4).icon)
                    binding!!.icon1.visibility = View.VISIBLE
                    binding!!.icon2.visibility = View.VISIBLE
                    binding!!.icon3.visibility = View.VISIBLE
                    binding!!.icon4.visibility = View.VISIBLE
                    binding!!.icon5.visibility = View.VISIBLE
                }
                else -> {

                }

            }
        }
    }

    // transition of icon with liquid animation
    override fun onValueChange(value: Float, position: Int) {

        when (position) {
            0 -> {
                binding!!.icon1.translationY =
                    (-binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value)
            }
            1 -> {
                binding!!.icon2.translationY =
                    (-binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value)
            }
            2 -> {
                binding!!.icon3.translationY =
                    (-binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value)
            }
            3 -> {
                binding!!.icon4.translationY =
                    (-binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value)
            }
            4 -> {
                binding!!.icon5.translationY =
                    (-binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value)
            }
        }
    }

    // transition of icon to the default position.
    override fun onValueDown(value: Float, position: Int) {

        when (position) {
            0 -> {
                binding!!.icon1.translationY =
                    -binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value
            }
            1 -> {
                binding!!.icon2.translationY =
                    -binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value
            }
            2 -> {
                binding!!.icon3.translationY =
                    -binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value
            }
            3 -> {
                binding!!.icon4.translationY =
                    -binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value
            }
            4 -> {
                binding!!.icon5.translationY =
                    -binding!!.bottomNavigationView.liquidNavbarVerticalOffset * value
            }
        }
    }

    //transition of other icons when selected tab icon is animating
    override fun onValueSelected(position: Int) {
        binding!!.icon1.translationY = 0f
        binding!!.icon2.translationY = 0f
        binding!!.icon3.translationY = 0f
        binding!!.icon4.translationY = 0f
        binding!!.icon5.translationY = 0f

    }

    //onNavigation Item selected for BottomNavigation
    override fun onNavigationItemSelected(indexOfItemSelected: Int) {
        onNavigationItemSelectListener!!.onNavigationItemSelected(indexOfItemSelected)

    }

    //listener for LiquidNavBar
    interface OnNavigationItemSelectListener {
        fun onNavigationItemSelected(indexOfItemSelected: Int): Boolean
    }
}