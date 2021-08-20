package com.mindinventory.liquidnavbar.utils


import kotlin.math.*

// GeometryUtils for making the liquid view
class GeometryUtils {

    companion object {

        fun getTangentsOfTwoCircles(
            x1: Double,
            y1: Double,
            r1: Double,
            x2: Double,
            y2: Double,
            r2: Double
        ): Array<DoubleArray?> {
            val dSq = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)
            if (dSq <= (r1 - r2) * (r1 - r2)) return Array(0) { DoubleArray(4) }

            val d = sqrt(dSq)
            val vx = (x2 - x1) / d
            val vy = (y2 - y1) / d

            val res = Array(4) { DoubleArray(4) }
            var i = 0

            var sign1 = +1
            while (sign1 >= -1) {
                val c = (r1 - sign1 * r2) / d

                // Now we're just intersecting a line with a circle: v*n=c, n*n=1
                if (c * c > 1.0) {
                    sign1 -= 2
                    continue
                }
                val h = sqrt(0.0.coerceAtLeast(1.0 - c * c))

                var sign2 = +1
                while (sign2 >= -1) {
                    val nx = vx * c - sign2.toDouble() * h * vy
                    val ny = vy * c + sign2.toDouble() * h * vx

                    val a = res[i++]
                    a[0] = x1 + r1 * nx
                    a[1] = y1 + r1 * ny
                    a[2] = x2 + sign1.toDouble() * r2 * nx
                    a[3] = y2 + sign1.toDouble() * r2 * ny
                    sign2 -= 2
                }
                sign1 -= 2
            }

            return res.copyOf(i)
        }

        fun getTangentsOfPointToCircle(
            px: Double,
            py: Double,
            cx: Double,
            cy: Double,
            r: Double
        ): Array<DoubleArray> {
            val res = Array(2) { DoubleArray(4) }

            // Calculate the tangent point of the circle
            val dx = cx - px
            val dy = cy - py

            val dist = sqrt((dx * dx + dy * dy))

            val a = asin((r / dist))

            // The attain2 function gives the angle of a point with respect to the X axis, given the point's x and y coordinates.
            val b = atan2(dy, dx)

            val theta1 = b - a
            val tanCPx1 = r * sin(theta1)
            val tanCPy1 = r * -cos(theta1)

            val theta2 = b + a
            val tanCPx2 = r * -sin(theta2)
            val tanCPy2 = r * cos(theta2)

            //The x point of the tangent
            val tanPx1 = cx + tanCPx1
            val tanPx2 = cx + tanCPx2

            //The y point of the tangent
            val tanPy1 = cy + tanCPy1
            val tanPy2 = cy + tanCPy2

            res[0][0] = px
            res[0][1] = py
            res[0][2] = tanPx1
            res[0][3] = tanPy1

            res[1][0] = px
            res[1][1] = py
            res[1][2] = tanPx2
            res[1][3] = tanPy2
            return res
        }

        fun angleBetweenPoints(cx: Double, cy: Double, px: Double, py: Double): Double {

            val theta = atan2(py - cy, px - cx)

            return Math.toDegrees(theta)
        }
    }
}