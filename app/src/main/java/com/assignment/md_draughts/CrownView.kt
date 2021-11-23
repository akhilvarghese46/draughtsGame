package com.assignment.md_draughts
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CrownView : View {

    private val paint = Paint()
    private val path = Path()
    private val mtx = Matrix()
    private var od = 0f

    private val circlePaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // view width ... padding not calculated
        var w = width
        var h = height

        // translate if any
        val dx = 0f
        val dy = 0f

        //draw circle
        val radius = (if (w < h) w else h)/2f
        canvas.drawCircle(w/2f, h/2f, radius, circlePaint)



        // crown drawing original size
        val ow = 200f
        val oh = 200f
        od = if (w / ow < h / oh) w / ow else h / oh
        canvas.save()
        canvas.translate((w - od * ow) / 2f + dx, (h - od * oh) / 2f + dy)

        mtx.reset()
        mtx.setScale(od, od)

        canvas.save()
        canvas.scale(1.0f,1.0f)
        canvas.save()
        path.reset()
        path.moveTo(24.5f,64.2f)
        path.lineTo(61.5f,82.6f)
        path.lineTo(61.5f,96.9f)
        path.cubicTo(61.5f,96.9f,69.0f,108.3f,79.5f,106.8f)
        path.cubicTo(90.0f,105.3f,91.9f,96.2f,92.6f,89.2f)
        path.lineTo(79.2f,66.0f)
        path.lineTo(100.8f,38.8f)
        path.lineTo(121.5f,66.3f)
        path.lineTo(108.8f,89.1f)
        path.cubicTo(108.8f,89.1f,110.1f,104.8f,121.5f,106.8f)
        path.cubicTo(132.9f,108.8f,136.4f,100.4f,138.9f,97.1f)
        path.lineTo(138.9f,83.3f)
        path.lineTo(177.5f,63.5f)
        path.lineTo(150.5f,139.5f)
        path.lineTo(51.5f,139.5f)
        path.lineTo(24.5f,64.2f)

        path.transform(mtx)
        canvas.drawPath(path, paint)
        canvas.restore()
        canvas.save()
        path.reset()
        path.moveTo(52.5f,147.2f)
        path.lineTo(148.6f,147.2f)
        path.quadTo(148.6f,147.2f,148.6f,147.2f)
        path.lineTo(148.6f,161.1f)
        path.quadTo(148.6f,161.1f,148.6f,161.1f)
        path.lineTo(52.5f,161.1f)
        path.quadTo(52.5f,161.1f,52.5f,161.1f)
        path.lineTo(52.5f,147.2f)
        path.quadTo(52.5f,147.2f,52.5f,147.2f)

        path.transform(mtx)
        canvas.drawPath(path, paint)
        canvas.restore()
   

    }



}