package de.dabotz.couchpotato

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.RectF


/**
 * Created by Botz on 05.12.17.
 */
public class CircleProgressView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val redPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val whitePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val grayPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val outerRect = RectF()
    private val innerRect = RectF()

    private val startAngle = -90f
    private val progressAngle: Float
        get() = progress.toFloat()/max.toFloat() * 360f
    private val stroke = 30f

    var max = 24
        set(value) {
            field = value
            invalidate()
        }
    var progress = 12

    init {
        redPaint.color = Color.RED
        whitePaint.color = Color.WHITE
        grayPaint.color = Color.LTGRAY
    }


    override fun onDraw(canvas: Canvas) {
        outerRect.set(0f,0f, width.toFloat(), width.toFloat())
        innerRect.set(0f,0f, width.toFloat(), width.toFloat())

        //draw progress arc
        canvas.drawArc(outerRect, startAngle, progressAngle, true, redPaint)
        //draw second arc to complete the circle
        canvas.drawArc(innerRect, startAngle + progressAngle, 360f - progressAngle, true, grayPaint)
        //Draw a circle over the arcs to get a stroke and not a filled circle
        canvas.drawCircle(width / 2.0f, width / 2.0f, ( width - stroke ) / 2f, whitePaint)
    }

}