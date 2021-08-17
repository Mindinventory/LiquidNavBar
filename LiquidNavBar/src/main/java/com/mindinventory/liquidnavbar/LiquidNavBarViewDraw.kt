package com.mindinventory.liquidnavbar

import android.graphics.Rect
import android.view.View
import androidx.core.view.forEachIndexed
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

// drawing the liquid view.
class LiquidNavBarViewDraw(

    private val bottomNavigationMenuView: BottomNavigationMenuView,
    var liquidTabItemRadius: Float,
    var liquidTabVerticalOffset: Float,
    var liquidTabCornerRadius: Float
) : EdgeTreatment() {
    private lateinit var magicPath: MagicPath

    var lastSelectedItem: Int = 0
    var selectedItem: Int = -1

    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {
        magicPath =
            MagicPath.create(0f, liquidTabVerticalOffset, length, liquidTabVerticalOffset)

        bottomNavigationMenuView.forEachIndexed { i, view ->
            var liquidTabHeightOffset = 0f


            if (i == selectedItem) {
                liquidTabHeightOffset = interpolation * liquidTabVerticalOffset
            }
            val itemRect = view.globalVisibleRect

            val centerRadius = liquidTabItemRadius
            val borderRadius = liquidTabCornerRadius
            val centerX = itemRect.centerX().toFloat()
            val centerY = liquidTabVerticalOffset + centerRadius - liquidTabHeightOffset

            val centerCircle =
                MagicPath.CircleShape(
                    centerX,
                    centerY,
                    centerRadius,
                    MagicPath.PathDirection.CLOCKWISE
                )

            val leftCircle = MagicPath.CircleShape(
                centerX,
                liquidTabVerticalOffset - borderRadius,
                borderRadius,
                MagicPath.PathDirection.C_CLOCKWISE
            )
            centerCircle.shiftOutside(leftCircle, MagicPath.ShiftMode.LEFT)

            val rightCircle = MagicPath.CircleShape(
                centerX,
                liquidTabVerticalOffset - borderRadius,
                borderRadius,
                MagicPath.PathDirection.C_CLOCKWISE
            )
            centerCircle.shiftOutside(rightCircle, MagicPath.ShiftMode.RIGHT)
            magicPath.addCircles(leftCircle, centerCircle, rightCircle)
        }

        magicPath.applyOn(shapePath)
    }

  private inline val View.globalVisibleRect: Rect
    get() {
      val r = Rect()
      getGlobalVisibleRect(r)
      return r
    }

}
