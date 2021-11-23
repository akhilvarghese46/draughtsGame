package com.assignment.md_draughts

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class DraughtsView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

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

   /* constructor(context: Context?, attribs: AttributeSet?) : this(context) {
        _attribs = attribs
    }*/


    init {
        playerOneCoin = Paint(Paint.ANTI_ALIAS_FLAG)
        playerTwoCoin = Paint(Paint.ANTI_ALIAS_FLAG)
        playerOneCoin.setColor(Color.argb(255, 0, 0, 255)) //blue
        playerTwoCoin.setColor(Color.argb(255, 0, 255, 0)) //green
        _black = Paint(Paint.ANTI_ALIAS_FLAG)
        _black.setColor(Color.argb(255, 0, 0, 0))
        lightColor = Color.parseColor("#EEEEEE")
        darkColor = Color.parseColor("#BBBBBB")
        initializeCoins()



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
            moveCoin(row, col)

            invalidate()
            return true
        } else if (event.actionMasked == MotionEvent.ACTION_MOVE) {

            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }
    public fun initializeCoins() {
        coinPosition.clear()

        for (j in 0 until 3) {
            for (i in 0 until 8) {
                paint.color = if ((j + i) % 2 == 1) darkColor else lightColor
                if (paint.color == darkColor) {
                    var obj: DraughtsCoins = DraughtsCoins(
                        colum = j.toInt(),
                        row = i.toInt(),
                        player = Players.PlayerTwo,
                        colour = playerTwoCoin,
                        isKing = false
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
                        colour = playerOneCoin,
                        isKing = false
                    )
                    coinPosition.add(obj)
                }
            }
        }
    }
    private fun moveCoin(row: Int, col: Int) {

        var isMove:Boolean=false
        var newRow= row
        var newColum = col
        if(fromCoin.row != row || fromCoin.colum != col){
            if(isCoinCanMove(fromCoin,row,col))
            {
                isMove=true
                for (obj in coinPosition)
                {
                    if(obj.row == row && obj.colum == col)
                    {
                        isMove=false
                        /*if(obj.player!=fromCoin.player){
                            isMove=false
                        }
                        else{
                            isMove=false
                        }*/

                    }
                }



            }else{
                if(isGetPointMove(fromCoin,row,col)){
                    isMove=false
                    val event: DraughtsCoins? = coinPosition.find { it.row == row && it.colum==col}
                    if(event!=null)
                        isMove=false
                    else{
                        if(fromCoin.player==Players.PlayerOne) {
                            val eventRight: DraughtsCoins? =
                                coinPosition.find { it.row == fromCoin.row - 1 && it.colum == fromCoin.colum - 1 && row + 1 == it.row && col + 1 == it.colum && fromCoin.player != it.player }
                            if (eventRight != null) {
                                isMove = true
                                coinPosition.remove(eventRight)
                            }
                            val eventLeft: DraughtsCoins? =
                                coinPosition.find { fromCoin.row + 1 == it.row && fromCoin.colum - 1 == it.colum && row - 1 == it.row && col + 1 == it.colum && fromCoin.player != it.player }
                            if (eventLeft != null) {
                                isMove = true
                                coinPosition.remove(eventLeft)
                            }
                        }else {
                            val eventRightPt: DraughtsCoins? =
                                coinPosition.find { it.row == fromCoin.row + 1 && it.colum == fromCoin.colum + 1 && row - 1 == it.row && col - 1 == it.colum && fromCoin.player != it.player }
                            if (eventRightPt != null) {
                                isMove = true
                                coinPosition.remove(eventRightPt)
                            }
                            val eventLeftPt: DraughtsCoins? =
                                coinPosition.find { fromCoin.row - 1 == it.row && fromCoin.colum + 1 == it.colum && row + 1 == it.row && col - 1 == it.colum && fromCoin.player != it.player }
                            if (eventLeftPt != null) {
                                isMove = true
                                coinPosition.remove(eventLeftPt)
                            }
                        }
                    }

                   /* for (obj in coinPosition)
                    {
                        var isDel = false
                        if(obj.row == row && obj.colum == col)
                        {
                            isMove=false
                            return

                        }
                        if (fromCoin.row - 1 == obj.row && fromCoin.colum - 1 == obj.colum && row + 1 == obj.row && col + 1 == obj.colum && fromCoin.player != obj.player) {
                            isDel = true
                            isMove = true
                        }

                        if (fromCoin.row + 1 == obj.row && fromCoin.colum - 1 == obj.colum && row-1 == obj.row && col+1 == obj.colum  && fromCoin.player != obj.player) {
                            isDel = true
                            isMove = true
                        }
                        if(isDel==true)
                            coinPosition.remove(obj)
                    }*/
                }

            }
            if(isMove) {

                coinPosition.remove(fromCoin)
                var newObj: DraughtsCoins = DraughtsCoins(
                    colum = newColum.toInt(),
                    row = newRow.toInt(),
                    player = fromCoin.player,
                    colour = fromCoin.colour,
                    isKing = false
                )
                coinPosition.add(newObj)
            }



        }


    }

    private fun isGetPointMove(frmCoinData: DraughtsCoins, toRow: Int, toColumn: Int): Boolean {
        var clumndrawcheck:Boolean = false
        if(frmCoinData.player.equals(Players.PlayerOne))
        {
            clumndrawcheck =toColumn.equals(frmCoinData.colum-2) && (toRow.equals(frmCoinData.row-2)||toRow.equals(frmCoinData.row+2))&&(toRow>=0&&toRow<=7&&toColumn>=0&&toColumn<=7)
            return clumndrawcheck
        }
        else{
            clumndrawcheck = toColumn.equals(frmCoinData.colum+2) && (toRow.equals(frmCoinData.row-2)||toRow.equals(frmCoinData.row+2))&&(toRow>=0&&toRow<=7&&toColumn>=0&&toColumn<=7)
            return clumndrawcheck
        }
    }

    private fun isCoinCanMove(frmCoinData: DraughtsCoins, toRow: Int, toColumn: Int):Boolean {
        var clumandrowcheck:Boolean = false
        if(frmCoinData.player.equals(Players.PlayerOne))
        {
            clumandrowcheck =toColumn.equals(frmCoinData.colum-1) && (toRow.equals(frmCoinData.row-1)||toRow.equals(frmCoinData.row+1))&&(toRow>=0&&toRow<=7&&toColumn>=0&&toColumn<=7)
            return clumandrowcheck
        }
        else{
            clumandrowcheck = toColumn.equals(frmCoinData.colum+1) && (toRow.equals(frmCoinData.row-1)||toRow.equals(frmCoinData.row+1))&&(toRow>=0&&toRow<=7&&toColumn>=0&&toColumn<=7)
            return clumandrowcheck
        }


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
        var w = canvas.width
        var h = canvas.height
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
           /* if(obj.isKing==false){

            }*/






            canvas.drawText(text, j , i, _black)
            canvas?.restore()
        }


    }

}