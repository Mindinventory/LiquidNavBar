package com.mindinventory.liquidnavbar

import com.google.android.material.shape.ShapePath
import java.util.*
import kotlin.math.sqrt

class MagicPath private constructor(
    private val startX: Float,
    private val startY: Float,
    private val endX: Float,
    private val endY: Float
) {

    private val circles: ArrayList<CircleShape> = ArrayList()

    companion object {
        fun create(startX: Float, startY: Float, endX: Float, endY: Float) =
            MagicPath(startX, startY, endX, endY)
    }

    fun applyOn(shapePath: ShapePath) {
        var px = startX
        var py = startY

        shapePath.lineTo(px, py)

        for (i in 0 until circles.size) {
            val circle = circles[i]
            val nextCircle = if (i < circles.size - 1) circles[i + 1] else null

            val (tPx, tPy) = getLineToCircle(px, py, circle)
            shapePath.lineTo(tPx.toFloat(), tPy.toFloat())

            var startAngle: Double
            var endAngle: Double

            // Calculate the common tangent between the current and the next circle
            if (nextCircle != null) {
                // All tangents between current circle and next circle
                val tangent: DoubleArray = getTangentForTwoCircles(circle, nextCircle)

                startAngle = GeometryUtils.angleBetweenPoints(circle.centerX.toDouble(), circle.centerY.toDouble(), tPx,
                    tPy)
                endAngle = GeometryUtils.angleBetweenPoints(circle.centerX.toDouble(), circle.centerY.toDouble(),
                    tangent[0], tangent[1])

                px = tangent[0].toFloat()
                py = tangent[1].toFloat()
            } else {
                // Draw last arc
                val tangentOfLastPointToCircle = getTangentForPointToCircle(circle)

                startAngle = GeometryUtils.angleBetweenPoints(circle.centerX.toDouble(), circle.centerY.toDouble(), tPx,
                    tPy)
                endAngle = GeometryUtils.angleBetweenPoints(circle.centerX.toDouble(), circle.centerY.toDouble(),
                    tangentOfLastPointToCircle[2],
                    tangentOfLastPointToCircle[3])
            }

            var sweepAngle = endAngle - startAngle
            if (circle.pathDirection == PathDirection.CLOCKWISE && sweepAngle < 0) {
                sweepAngle += 360
            } else if (circle.pathDirection == PathDirection.C_CLOCKWISE && sweepAngle > 0) {
                sweepAngle -= 360
            }

            shapePath.addArc(circle.left,
                circle.top,
                circle.right,
                circle.bottom,
                startAngle.toFloat(),
                sweepAngle.toFloat()
            )
        }

        shapePath.lineTo(endX, endY)
    }

    private fun getTangentForTwoCircles(
        circle: CircleShape,
        nextCircle: CircleShape
    ): DoubleArray {
        val tangents = GeometryUtils.getTangentsOfTwoCircles(
            circle.centerX.toDouble(),
            circle.centerY.toDouble(),
            circle.radius.toDouble(),
            nextCircle.centerX.toDouble(),
            nextCircle.centerY.toDouble(),
            nextCircle.radius.toDouble()
        )

        return if (circle.pathDirection == nextCircle.pathDirection) {

            if (circle.pathDirection == PathDirection.C_CLOCKWISE) tangents[0]!! else tangents[1]!!
        } else {
            if (circle.pathDirection == PathDirection.C_CLOCKWISE) tangents[2]!! else tangents[3]!!
        }
    }


    fun addCircles(vararg circles: CircleShape) {
        this.circles.addAll(circles)
    }

    private fun getTangentForPointToCircle(circle: CircleShape): DoubleArray {
        val tangentsOfLastPointToCircle = GeometryUtils.getTangentsOfPointToCircle(
            endX.toDouble(),
            endY.toDouble(),
            circle.centerX.toDouble(),
            circle.centerY.toDouble(),
            circle.radius.toDouble())
        return tangentsOfLastPointToCircle[if (circle.pathDirection == PathDirection.C_CLOCKWISE) 0 else 1]
    }

    private fun getLineToCircle(px: Float, py: Float,
                                circle: CircleShape): Pair<Double, Double> {
        val tangentsOfPointToCircle = GeometryUtils.getTangentsOfPointToCircle(
            px.toDouble(),
            py.toDouble(),
            circle.centerX.toDouble(),
            circle.centerY.toDouble(),
            circle.radius.toDouble())
        val tangentOfPointToCircle = tangentsOfPointToCircle[if (circle.pathDirection == PathDirection.CLOCKWISE) 0 else 1]

        val tPx = tangentOfPointToCircle[2]
        val tPy = tangentOfPointToCircle[3]
        return Pair(tPx, tPy)
    }

    open class CircleShape(var centerX: Float, var centerY: Float, var radius: Float, var pathDirection: PathDirection) {
        fun shiftOutside(circle: CircleShape, shift: ShiftMode) {
            if (shift == ShiftMode.LEFT || shift == ShiftMode.RIGHT) {
                val difY = circle.centerY - centerY
                val distance = circle.radius + radius

                val difX = sqrt((distance * distance - difY * difY).toDouble()) + 0.1
                val sign = if (shift == ShiftMode.LEFT) -1 else 1
                circle.centerX = (centerX + difX * sign).toFloat()
            } else {
                val difX = circle.centerX - centerX
                val distance = circle.radius + radius

                val difY = sqrt((distance * distance - difX * difX).toDouble()) + 0.1
                val sign = if (shift == ShiftMode.TOP) -1 else 1
                circle.centerX = (centerX + difY * sign).toFloat()
            }
        }

        val top: Float get() = centerY - radius
        val left: Float get() = centerX - radius
        val bottom: Float get() = centerY + radius
        val right: Float get() = centerX + radius

    }

    enum class ShiftMode {
        LEFT, RIGHT, TOP
    }

    enum class PathDirection {
        CLOCKWISE, C_CLOCKWISE
    }
}
