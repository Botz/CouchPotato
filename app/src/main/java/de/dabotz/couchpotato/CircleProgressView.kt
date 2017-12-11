package de.dabotz.couchpotato

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.concurrent.TimeUnit


/**
 * Created by Botz on 05.12.17.
 */
open class CircleProgressView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = Color.RED
    }
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = Color.WHITE
    }
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = Color.LTGRAY
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textSize =  context.resources.getDimension(R.dimen.circleProgressTextSize)
        it.color = Color.GRAY
        it.textAlign = Paint.Align.CENTER
    }
    private val bottomTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textSize =  context.resources.getDimension(R.dimen.circleProgressTargetTextSize)
        it.color = Color.LTGRAY
        it.textAlign = Paint.Align.CENTER
    }
    private val outerRect = RectF()
    private val innerRect = RectF()
    private val textRect = Rect()

    private val startAngle = -90f
    private val progressAngle: Float
        get() = progress.toFloat()/max.toFloat() * 360f
    private val stroke = 30f

    var max: Long = 24
        set(value) {
            field = value
            invalidate()
        }
    var progress: Long = 12
        set(value) {
            println("Current VALUE: $value")
            field = Math.min(value, max)
            invalidate()
        }

    open fun getProgresstext() = "${TimeUnit.MILLISECONDS.toMinutes(progress)/60} Stunden ${TimeUnit.MILLISECONDS.toMinutes(progress)%60} Minuten"
    open fun getTargetText() = "Ziel: ${TimeUnit.MILLISECONDS.toMinutes(max)/60} Stunden"

    override fun onDraw(canvas: Canvas) {
        outerRect.set(0f,0f, width.toFloat(), height.toFloat())
        innerRect.set(0f,0f, width.toFloat(), height.toFloat())

        //draw progress arc
        canvas.drawArc(outerRect, startAngle, progressAngle, true, progressPaint)
        //draw second arc to complete the circle
        canvas.drawArc(innerRect, startAngle + progressAngle, 360f - progressAngle, true, circlePaint)
        //Draw a circle over the arcs to get a stroke and not a filled circle
        canvas.drawCircle(width / 2.0f, height / 2.0f, ( width - stroke ) / 2f, backgroundPaint)


        val progressText = getProgresstext()
        val targetText = getTargetText()
        textPaint.getTextBounds(progressText, 0, progressText.length, textRect)
        val yPosProgressText = height / 2f - (textRect.bottom - textRect.top)
        textPaint.getTextBounds(targetText, 0, targetText.length, textRect)
        val yPosTargetText = height / 2f + (textRect.bottom - textRect.top)
        canvas.drawText(progressText, width / 2f, yPosProgressText, textPaint)
        canvas.drawText(targetText, width / 2f, yPosTargetText, bottomTextPaint)
    }

}