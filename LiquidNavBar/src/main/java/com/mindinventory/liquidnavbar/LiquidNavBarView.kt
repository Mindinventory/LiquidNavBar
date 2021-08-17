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
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class LiquidNavBarView : BottomNavigationView, NavigationBarView.OnItemSelectedListener {
    private val height: Float
    private val topEdgeTreatment: LiquidNavBarViewDraw
    private val materialShapeDrawable: MaterialShapeDrawable
    private var isAnimationFinish: Boolean = true
    private var selectedItem = 0
    private var selectionAnimator: ValueAnimator? = null
    private var deselectAnimator: ValueAnimator? = null

    private var liquidNavbarItemRadius: Float = 0f
        set(radius) {
            field = radius
            topEdgeTreatment.liquidNavbarItemRadius = radius
            invalidate()
        }

    private var liquidNavbarCornerRadius: Float = 0f
        set(radius) {
            field = radius
            topEdgeTreatment.liquidNavbarCornerRadius = radius
            invalidate()
        }

    var liquidNavbarVerticalOffset: Float = 0f
        set(offset) {
            field = offset
            topEdgeTreatment.liquidNavbarVerticalOffset = offset
            if (layoutParams != null) {
                layoutParams.height = (height + liquidNavbarVerticalOffset).toInt()
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

        val a = resources.obtainAttributes(attrs, R.styleable.LiquidNavbarView)

        val typedValue = TypedValue()
        context.theme?.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        val backgroundTint =
            a.getColor(R.styleable.LiquidNavbarView_backgroundTint, typedValue.data)

        val liquidNavbarItemRadius = a.getDimensionPixelSize(
            R.styleable.LiquidNavbarView_liquidNavbarItemRadius,
            dpToPx(64f).toInt()
        ).toFloat()
        val liquidNavbarVerticalOffset = a.getDimensionPixelSize(
            R.styleable.LiquidNavbarView_liquidNavbarVerticalOffset,
            dpToPx(8f).toInt()
        ).toFloat()
        val liquidNavbarCornerRadius = a.getDimensionPixelSize(
            R.styleable.LiquidNavbarView_liquidNavbarCornerRadius,
            dpToPx(128f).toInt()
        ).toFloat()
        a.recycle()

        height = dpToPx(56f)

        bottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView

        topEdgeTreatment = LiquidNavBarViewDraw(
            bottomNavigationMenuView,
            liquidNavbarItemRadius,
            liquidNavbarVerticalOffset,
            liquidNavbarCornerRadius
        )

        this.liquidNavbarItemRadius = liquidNavbarItemRadius
        this.liquidNavbarVerticalOffset = liquidNavbarVerticalOffset
        this.liquidNavbarCornerRadius = liquidNavbarCornerRadius

        val shapePathModel =
            ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 30f)
                .setTopEdge(topEdgeTreatment).build()

        materialShapeDrawable = MaterialShapeDrawable(shapePathModel)
        materialShapeDrawable.shadowCompatibilityMode
        materialShapeDrawable.paintStyle = Style.FILL
        materialShapeDrawable.setTint(backgroundTint)
        background = materialShapeDrawable

        val menuParams = bottomNavigationMenuView.layoutParams as LayoutParams
        menuParams.gravity = (Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)

        setOnItemSelectedListener(this)
        setOnItemSelectedListener {
            onNavigationItemSelected(it)
        }
        setWillNotDraw(false)
    }


    var animationListener: AnimationListener? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val indexOfItemSelected = menu.indexOfItem(item)
        animationListener?.onValueSelected(indexOfItemSelected)
        animationListener?.onNavigationItemSelected(indexOfItemSelected)

        if (indexOfItemSelected != selectedItem) {
            isAnimationFinish = false
            topEdgeTreatment.lastSelectedItem = selectedItem
            topEdgeTreatment.selectedItem = indexOfItemSelected
            selectedItem = indexOfItemSelected
            selectionAnimator = ValueAnimator.ofFloat(0f, 0.2f, 0.4f, 0.6f, 0.8f, 1f)

            deselectAnimator = ValueAnimator.ofFloat(1f, 0.8f, 0.6f, 0.4f, 0.2f, 0f)
            deselectAnimator?.addUpdateListener {
                isAnimationFinish = it.animatedValue == 0f

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
            selectionAnimator?.duration = 400
            deselectAnimator?.duration = 10

            selectionAnimator?.interpolator = DecelerateInterpolator()
            deselectAnimator?.interpolator = DecelerateInterpolator()
            selectionAnimator?.start()
        }
        return true
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Change height
        layoutParams.height = (height + liquidNavbarVerticalOffset).toInt()
    }

    override fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        super.setOnItemSelectedListener {
            if (isAnimationFinish) {
                onNavigationItemSelected(it)
                if (listener !is LiquidNavBarView) {
                    listener?.onNavigationItemSelected(it)
                }
                true
            } else {
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

