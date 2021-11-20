package com.assignment.md_draughts

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class DraughtsView(context: Context?) : View(context) {

    private var _attribs: AttributeSet? = null
    private var cellSize = 0f
    private var lightColor = Color.parseColor("#EEEEEE")
    private var darkColor = Color.parseColor("#BBBBBB")
    private val paint = Paint()
    private var playerOneCoin: Paint
    private var playerTwoCoin: Paint
    private var frmColumn: Int =0
    private var frmRow: Int = 0
    private lateinit var _black: Paint
    var coinPosition = arrayListOf<DraughtsCoins>()
    lateinit var fromCoin:DraughtsCoins

    constructor(context: Context?, attribs: AttributeSet?) : this(context) {
        _attribs = attribs
    }


    init {
        playerOneCoin = Paint(Paint.ANTI_ALIAS_FLAG)
        playerTwoCoin = Paint(Paint.ANTI_ALIAS_FLAG)
        playerOneCoin.setColor(Color.argb(255, 0, 0, 255))
        playerTwoCoin.setColor(Color.argb(255, 0, 255, 0))
        _black = Paint(Paint.ANTI_ALIAS_FLAG)
        _black.setColor(Color.argb(255, 0, 0, 0))
        lightColor = Color.parseColor("#EEEEEE")
        darkColor = Color.parseColor("#BBBBBB")



        for (j in 0 until 3) {
            for (i in 0 until 8) {
                paint.color = if ((j + i) % 2 == 1) darkColor else lightColor
                if (paint.color == lightColor) {
                    var obj: DraughtsCoins = DraughtsCoins(
                        colum = j.toInt(),
                        row = i.toInt(),
                        player = Players.PlayerTwo,
                        colour = playerTwoCoin
                    )

                    coinPosition.add(obj)
                }
            }
        }

        for (j in 5 until 8) {
            for (i in 0 until 8) {
                paint.color = if ((j + i) % 2 == 1) darkColor else lightColor
                if (paint.color == darkColor) {
                    var obj: DraughtsCoins = DraughtsCoins(
                        colum = j.toInt(),
                        row = i.toInt(),
                        player = Players.PlayerOne,
                        colour = playerOneCoin
                    )
                    coinPosition.add(obj)
                }
            }
        }

    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val smaller = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(smaller, smaller)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        var width: Int = canvas!!.width
        var height: Int = canvas!!.height
        drwDraughtsCanvas(canvas)
        drwCoin(canvas)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event!!.actionMasked == MotionEvent.ACTION_DOWN || event!!.actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
            frmRow = (event.x  / cellSize).toInt()
            frmColumn = (event.y  / cellSize).toInt()
            for (obj in coinPosition)
            {
                if(obj.row == frmRow && obj.colum == frmColumn)
                {
                    fromCoin = obj
                }
            }
            invalidate()
            return true
        } else if (event!!.actionMasked == MotionEvent.ACTION_UP || event!!.actionMasked == MotionEvent.ACTION_POINTER_UP) {
            val row = (event.x  / cellSize).toInt()
            val col = (event.y  / cellSize).toInt()
            if(fromCoin.row != row && fromCoin.row != col){
                coinPosition.remove(fromCoin)
                var obj: DraughtsCoins = DraughtsCoins(
                    colum = col.toInt(),
                    row = row.toInt(),
                    player = fromCoin.player,
                    colour = fromCoin.colour
                )
                coinPosition.add(obj)
            }

            invalidate()
            return true
        } else if (event.actionMasked == MotionEvent.ACTION_MOVE) {

            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }


    private fun drwDraughtsCanvas(canvas: Canvas) {
        var width: Int = canvas!!.width
        var height: Int = canvas!!.height
        //val size = min(width, height)
        cellSize = width / 8f

        // topDistance = (height - width) / 2f


        for (i in 0 until 8) {

            for (j in 0 until 8) {
                paint.color = if ((j + i) % 2 == 1) darkColor else lightColor


                canvas?.drawRect(
                    j * cellSize,
                    i * cellSize,
                    (j + 1) * cellSize,
                    (i + 1) * cellSize,
                    paint
                )
                var text = "("+i+"-"+j+")"
               canvas.drawText(text, j*1.0f* cellSize , (i + 1) * cellSize, _black)

            }
        }

    }


    private fun drwCoin(canvas: Canvas) {
        for (obj in coinPosition) {
            canvas?.save()
            canvas?.translate(
                (obj.row * cellSize) + cellSize / 2.0f,
                (obj.colum * cellSize) + cellSize / 2.0f
            )
            var k = obj.colum
            var m=obj.row
            var i = (obj.colum * 1.0f)
            var j=  (obj.row *1.0f)
            var text = "["+k+"-"+m+"]"

            canvas?.drawCircle(0.0f, 0.0f, cellSize / 3.0f, obj.colour)
            canvas.drawText(text, j , i, _black)
            canvas?.restore()
        }
        /*

            for (j in 0 until 3) {
                for (i in 0 until 8) {
                    paint.color = if ((j + i) % 2 == 1) darkColor else lightColor
                    if (paint.color == lightColor) {
                        var obj: DraughtsCoins = DraughtsCoins(
                            colum = j.toInt(),
                            row = i.toInt(),
                            player = Players.PlayerTwo
                        )
                        canvas?.save()
                        canvas?.translate(
                            (i * cellSize) + cellSize / 2.0f,
                            (j * cellSize) + cellSize / 2.0f
                        )
                        canvas?.drawCircle(0.0f, 0.0f, cellSize / 3.0f, playerTwoCoin)
                        canvas?.restore()
                        coinPosition.add(obj)
                    }
                }
            }

            for (j in 5 until 8) {
                for (i in 0 until 8) {
                    paint.color = if ((j + i) % 2 == 1) darkColor else lightColor
                    if (paint.color == darkColor) {
                        var obj: DraughtsCoins = DraughtsCoins(
                            colum = j.toInt(),
                            row = i.toInt(),
                            player = Players.PlayerOne
                        )
                        canvas?.save()
                        canvas?.translate(
                            (i * cellSize) + cellSize / 2.0f,
                            (j * cellSize) + cellSize / 2.0f
                        )
                        canvas?.drawCircle(0.0f, 0.0f, cellSize / 3.0f, playerOneCoin)
                        canvas?.restore()
                        coinPosition.add(obj)
                    }
                }
            }*/

    }

}