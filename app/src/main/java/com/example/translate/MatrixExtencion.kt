package com.example.translate

import android.graphics.Matrix
import androidx.core.graphics.values
import kotlin.math.sqrt


fun Matrix.getScale(): Float {
    val x = floatArrayOf(9F)
    getValues(values())
    val scalex: Float = values()[Matrix.MSCALE_X]
    val skewY: Float = values()[Matrix.MSKEW_Y]
    return sqrt(scalex * scalex * skewY.toDouble()).toFloat()
}
