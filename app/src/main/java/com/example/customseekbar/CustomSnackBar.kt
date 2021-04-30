package com.example.customseekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.lang.Math.abs

class CustomSnackBar @JvmOverloads constructor(
    context: Context,
    attributSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attributSet, defStyleAttr) {

    private val circlePaint =
        Paint(Paint.ANTI_ALIAS_FLAG) // Anti alias çizimin cam gibi dümdüz olmasını sağlar

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val transparentLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val viewRect =
        RectF() //Todo Tüm ekranı algılamak için viewRect oluşturduk. Sorna set ettik
    private val thumbCircleRadius =
        context.resources.getDimensionPixelSize(R.dimen.thumb_circle_radius)
    private val thumbCircleRectangle = RectF()

    private val min = -50
    private val max = 50

    init {
        circlePaint.apply {
            style =
                Paint.Style.FILL_AND_STROKE // Fill dersek cemberin içini doldurur,Fill and stroke dersek hem boyar hem stroke atar
            strokeWidth = 8f
            color = Color.RED
        }
        linePaint.apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 8f
            color = Color.BLUE
        }
        transparentLinePaint.apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 8f
            color = Color.GRAY
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //Todo
        viewRect.set(0f, 0f, w.toFloat(), h.toFloat()) //çerçeveyi ölçtük
        thumbCircleRectangle.set( //Todo
            viewRect.centerX() - thumbCircleRadius,
            viewRect.centerY() - thumbCircleRadius,
            viewRect.centerX() + thumbCircleRadius,
            viewRect.centerY() + thumbCircleRadius
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //Todo elimizi koyduğumuz da ki hareketler geliyor
        when (event.action) {
            MotionEvent.ACTION_DOWN -> { //Todo Kullanıcı ilk parmağını dokunduğu noktalar
                calculateProgress(event)
                //Todo dokunduğun noktaya taşıyacağım. Sadece sol ve sağını değiştireceğiz.
                thumbCircleRectangle.left = event.x - thumbCircleRadius
                thumbCircleRectangle.right = event.x + thumbCircleRadius
                invalidate() // Şuanki halini invalidate yap hareketi yakalarsın ondraw cağrılmadığı için anlam ifade etmez kesinlikle invalidate yapman lazım.
            }
            MotionEvent.ACTION_MOVE -> {
                calculateProgress(event)
                thumbCircleRectangle.left = event.x - thumbCircleRadius
                thumbCircleRectangle.right = event.x + thumbCircleRadius
                invalidate() //Todo tıklama ile değil kaydırma ile yapmak için
            }
        }
        return true
    }

    private fun calculateProgress(event: MotionEvent) {
        val multiplier = if (event.x < viewRect.centerX()) min else max

        val difference = abs(event.x - viewRect.centerX())

        val progress = difference / viewRect.centerX() * multiplier

        Log.d("Tag", progress.toString())
    }

    override fun onDraw(canvas: Canvas) { //Todo resim kagıdının tamamı canvas.. Bunun üzerine pozisyon yerlerini belirteerek birşey çizeceğiz
        super.onDraw(canvas) // Todo en önemli şey cordinat. 50x50 den başlayarak bir daire çiz mesela. Cordinat sol üstten 0x0 olacak şekilde başlar. Sağa doğru X düzeyin Aşşağı doğru Y düzezyinde artar
        //Todo canvas.draw diyince rectangle/drawArc/drawOval çizebilirsin mesela
        //canvas.drawCircle(50f, 50f, 25f, circlePaint) // yuvarlak
        //canvas.drawRect(100f, 25f, 200f, 225f, circlePaint) //dikdörtgen


        canvas.drawCircle(
            thumbCircleRectangle.centerX(),
            thumbCircleRectangle.centerY(),
            thumbCircleRadius.toFloat(),
            circlePaint
        )
        canvas.drawLine(
            0f,
            thumbCircleRectangle.centerY(),
            thumbCircleRectangle.centerX() - thumbCircleRadius,
            thumbCircleRectangle.centerY(),
            linePaint
        )
        canvas.drawLine(
            thumbCircleRectangle.centerX() + thumbCircleRadius,
            thumbCircleRectangle.centerY(),
            viewRect.width(),
            thumbCircleRectangle.centerY(),
            transparentLinePaint
        )
    }
}