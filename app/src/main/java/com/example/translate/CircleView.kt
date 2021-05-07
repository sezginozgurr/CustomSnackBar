package com.example.translate

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.customseekbar.R

class CircleView @JvmOverloads constructor( //construtorlar overload et
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private val shapePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val textPaint = TextPaint()

    private var viewRectangle = RectF()

    private var circleRectangle = RectF()

    private var zoomMatrix = Matrix()

    private val zoomBitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background)
    private var textWidth = 0f
    private val bitmapSize =
        context.resources.getDimensionPixelSize(R.dimen.bitmap_size)
            .toFloat() //pixeli çevirmek için kullan

    init {
        //painti init edicez
        shapePaint.apply {
            color = Color.BLUE //kalemin rengi
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }
        textPaint.apply {
            textSize = 12f
            color = Color.BLACK
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh) //büyüklüğünü belirlemek için
        viewRectangle.set(0f, 0f, w.toFloat(), h.toFloat()) //çerçeveyi ölçtük
        circleRectangle.set(
            viewRectangle.centerX() - SHAPE_RADIUS - SHAPE_PADDING,
            viewRectangle.centerY() - SHAPE_RADIUS - SHAPE_PADDING,
            viewRectangle.centerX() + SHAPE_RADIUS + SHAPE_PADDING,
            viewRectangle.centerY() + SHAPE_RADIUS + SHAPE_PADDING,
        )
        //textWidth = textPaint.measureText("Hello") ortasına text yazma
        updateZoomButtonPosition()
    }

    private fun updateZoomButtonPosition() {
        val scaleFactor = bitmapSize / zoomBitmap.width

        Log.e("ScaleFactor", "Scale:$scaleFactor")

        val translateX = circleRectangle.right - (scaleFactor * zoomBitmap.width) / 2f

        val translateY = circleRectangle.bottom - (scaleFactor * zoomBitmap.width) / 2f

        zoomMatrix.setScale(
            scaleFactor,
            scaleFactor
        ) //hem x noktasında hem y noktasında o kadar scale edecek. Buda direkt bitmape uygulanıyor

        /**matrix kullandığın zaman üstte sadece scale bilgisi yer alır. altta da sadece translate bilgisi yer alır.
        Matrix i kullanma nedeni bitmapi scale etmek için.. Resmi istediğim gibi scale etmek için */
        zoomMatrix.postTranslate(translateX, translateY) //setTranslate dersen sadece
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas?.drawRect(circleRectangle, shapePaint) //radius circle büyüklüğünü belirler
        //canvas?.drawText("Hello",viewRectangle.centerX(),viewRectangle.centerY(),textPaint) ortasına text yazma
        canvas?.drawCircle(
            circleRectangle.centerX(),
            circleRectangle.centerY(),
            SHAPE_RADIUS,
            shapePaint
        )
        canvas?.drawBitmap(zoomBitmap, zoomMatrix, shapePaint) //scalefaktor oranında
    }

    companion object {
        private const val SHAPE_PADDING = 16f
        private const val SHAPE_RADIUS = 56f
    }
}


