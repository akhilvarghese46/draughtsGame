package com.assignment.md_draughts

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class draughtsView(context: Context?) : View(context) {

    private var _attribs: AttributeSet? = null
    private val scaleFactor = 1.0f
    private var topDistance = 0f
    private var cellSize = 0f
    private var lightColor = Color.parseColor("#EEEEEE")
    private var darkColor = Color.parseColor("#BBBBBB")
    private val paint = Paint()
    private lateinit var playerOneCoin: Paint
    private lateinit var playerTwoCoin: Paint



    constructor(context: Context?, attribs: AttributeSet?) : this(context) {
        _attribs = attribs
    }


    init {
        playerOneCoin = Paint(Paint.ANTI_ALIAS_FLAG)
        playerTwoCoin = Paint(Paint.ANTI_ALIAS_FLAG)
        playerOneCoin.setColor(Color.argb(255, 0, 0, 255))
        playerTwoCoin.setColor(Color.argb(255, 0, 255, 0))

        lightColor = Color.parseColor("#EEEEEE")
        darkColor = Color.parseColor("#BBBBBB")
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        var width: Int = canvas!!.width
        var height: Int = canvas!!.height
        drwDraughtsCanvas(canvas)
        drwCoin(canvas)

    }


    private fun drwDraughtsCanvas(canvas: Canvas) {
        var width: Int = canvas!!.width
        var height: Int = canvas!!.height
        //val size = min(width, height)
        cellSize = width / 8f

       // topDistance = (height - width) / 2f


        for (i in 0 until 8)
            for (j in 0 until 8) {
                paint.color = if ((j + i) % 2 == 1) darkColor else lightColor
                canvas?.save()
                canvas?.drawRect(
                    j * cellSize,
                    i * cellSize,
                    (j + 1) * cellSize,
                    (i + 1) * cellSize,
                    paint
                )
                canvas?.restore()
            }

    }


    private fun drwCoin(canvas: Canvas) {
        for (j in 0 until 3) {
            for (i in 0 until 8) {
                paint.color = if ((j + i) % 2 == 1) darkColor else lightColor
                if (paint.color == lightColor) {
                    canvas?.save()
                    canvas?.translate(
                        (i * cellSize) + cellSize / 2.0f,
                        (j * cellSize) + cellSize / 2.0f
                    )
                    canvas?.drawCircle(0.0f, 0.0f, cellSize / 3.0f, playerOneCoin)
                    canvas?.restore()
                }
            }
        }

        for (j in 5 until 8) {
            for (i in 0 until 8) {
                paint.color = if ((j + i) % 2 == 1) darkColor else lightColor
                if (paint.color == darkColor) {
                    canvas?.save()
                    canvas?.translate(
                        (i * cellSize) + cellSize / 2.0f,
                        (j * cellSize) + cellSize / 2.0f
                    )
                    canvas?.drawCircle(0.0f, 0.0f, cellSize / 3.0f, playerTwoCoin)
                    canvas?.restore()
                }
            }
        }

    }

}