package com.mindinventory.liquidnavbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.animation.DecelerateInterpolator
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class LiquidNavBarView : BottomNavigationView, NavigationBarView.OnItemSelectedListener {
    companion object {
        const val DOUBLE_CLICK_TIME_DELTA: Long = 800 //milliseconds
    }

    private val height: Float
    private var lastClickTime: Long = 0
    private val topEdgeTreatment: LiquidNavBarViewDraw
    private val materialShapeDrawable: MaterialShapeDrawable

    private var selectedItem = 0

    private var selectionAnimator: ValueAnimator? = null
    private var deselectAnimator: ValueAnimator? = null

    private var liquidTabItemRadius: Float = 0f
        set(radius) {
            field = radius
            topEdgeTreatment.liquidTabItemRadius = radius
            invalidate()
        }

    private var liquidTabCornerRadius: Float = 0f
        set(radius) {
            field = radius
            topEdgeTreatment.liquidTabCornerRadius = radius
            invalidate()
        }

    var liquidTabVerticalOffset: Float = 0f
        set(offset) {
            field = offset
            topEdgeTreatment.liquidTabVerticalOffset = offset
            if (layoutParams != null) {
                layoutParams.height = (height + liquidTabVerticalOffset).toInt()
            }

            invalidate()
        }


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var bottomNavigationMenuView: BottomNavigationMenuView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs,
        defStyleAttr
    ) {

        val a = resources.obtainAttributes(attrs, R.styleable.LiquidTabBarView)

        val typedValue = TypedValue()
        context.theme?.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        val backgroundTint =
            a.getColor(R.styleable.LiquidTabBarView_backgroundTint, typedValue.data)

        val liquidTabItemRadius = a.getDimensionPixelSize(
            R.styleable.LiquidTabBarView_liquidTabItemRadius,
            dpToPx(64f).toInt()
        ).toFloat()
        val liquidTabVerticalOffset = a.getDimensionPixelSize(
            R.styleable.LiquidTabBarView_liquidTabVerticalOffset,
            dpToPx(8f).toInt()
        ).toFloat()
        val liquidTabCornerRadius = a.getDimensionPixelSize(
            R.styleable.LiquidTabBarView_liquidTabCornerRadius,
            dpToPx(128f).toInt()
        ).toFloat()
        a.recycle()

        height = dpToPx(56f)

        bottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView

        topEdgeTreatment = LiquidNavBarViewDraw(
            bottomNavigationMenuView,
            liquidTabItemRadius,
            liquidTabVerticalOffset,
            liquidTabCornerRadius
        )

        this.liquidTabItemRadius = liquidTabItemRadius
        this.liquidTabVerticalOffset = liquidTabVerticalOffset
        this.liquidTabCornerRadius = liquidTabCornerRadius

        val shapePathModel = ShapeAppearanceModel().toBuilder().setTopEdge(topEdgeTreatment).build()
        materialShapeDrawable = MaterialShapeDrawable(shapePathModel)
        materialShapeDrawable.shadowCompatibilityMode
        materialShapeDrawable.paintStyle = Style.FILL
        materialShapeDrawable.setTint(backgroundTint)
        background = materialShapeDrawable

        val menuParams = bottomNavigationMenuView.layoutParams as LayoutParams
        menuParams.gravity = (Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)

        setOnItemSelectedListener(this)
        setOnItemSelectedListener {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime >= DOUBLE_CLICK_TIME_DELTA) {
                onNavigationItemSelected(it)
                lastClickTime = clickTime
                true
            } else {
                lastClickTime = clickTime
                false
            }
        }
        setWillNotDraw(false)
    }



    var animationListener: AnimationListener? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val indexOfItemSelected = menu.indexOfItem(item)
        animationListener?.onValueSelected(indexOfItemSelected)
        animationListener?.onNavigationItemSelected(indexOfItemSelected)

        if (indexOfItemSelected != selectedItem) {
            topEdgeTreatment.lastSelectedItem = selectedItem
            topEdgeTreatment.selectedItem = indexOfItemSelected
            selectedItem = indexOfItemSelected
            selectionAnimator = ValueAnimator.ofFloat(0f, 0.2f, 0.4f, 0.6f, 0.8f, 1f)

            deselectAnimator = ValueAnimator.ofFloat(1f, 0.8f, 0.6f, 0.4f, 0.2f, 0f)
            deselectAnimator?.addUpdateListener {
                materialShapeDrawable.interpolation = it.animatedValue as Float
                animationListener?.onValueDown(it.animatedValue as Float, indexOfItemSelected)

            }
            selectionAnimator?.addUpdateListener {
                animationListener?.onValueChange(it.animatedValue as Float, indexOfItemSelected)
                materialShapeDrawable.interpolation = it.animatedValue as Float

                if (it.animatedValue == 1.0f) {
                    deselectAnimator?.start()
                }
            }
            selectionAnimator?.duration = 600
            deselectAnimator?.duration = 200

            selectionAnimator?.interpolator = DecelerateInterpolator()
            deselectAnimator?.interpolator = DecelerateInterpolator()
            selectionAnimator?.start()
        }
        return true
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Change height
        layoutParams.height = (height + liquidTabVerticalOffset).toInt()
    }

    override fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        super.setOnItemSelectedListener {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime >= DOUBLE_CLICK_TIME_DELTA) {
                onNavigationItemSelected(it)
                if (listener !is LiquidNavBarView) {
                    listener?.onNavigationItemSelected(it)
                }
                lastClickTime = clickTime
                true
            } else {
                lastClickTime = clickTime
                false
            }
        }

    }


    private fun dpToPx(dp: Float): Float {
        return resources.displayMetrics.density * dp
    }

    private fun Menu.indexOfItem(item: MenuItem): Int {
        for (i in 0 until this.size()) {
            val menuItem = menu.getItem(i)
            if (menuItem.itemId == item.itemId) {
                return i
            }
        }
        return -1
    }
}

