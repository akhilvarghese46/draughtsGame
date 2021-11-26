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
    public lateinit var lightColor: Paint
    public lateinit var darkColor: Paint
    public lateinit var colourPosition: Paint
    private val paint = Paint()
    public var playerOneCoin: Paint
    public var playerTwoCoin: Paint
    private var frmColumn: Int =0
    private var frmRow: Int = 0
    private lateinit var _black: Paint
    var coinPosition = arrayListOf<DraughtsCoins>()
    lateinit var fromCoin:DraughtsCoins
    var playerOneCoinNumber:Int=12
    var playerTwoCoinNumber:Int=12
    var changeListner:OnChangeListnerFromDraughtView?=null
    var nextMove:Players = Players.PlayerOne
   /* constructor(context: Context?, attribs: AttributeSet?) : this(context) {
        _attribs = attribs
    }*/


    init {
        darkColor = Paint(Paint.ANTI_ALIAS_FLAG)
        lightColor = Paint(Paint.ANTI_ALIAS_FLAG)
        playerOneCoin = Paint(Paint.ANTI_ALIAS_FLAG)
        playerTwoCoin = Paint(Paint.ANTI_ALIAS_FLAG)
        colourPosition = Paint(Paint.ANTI_ALIAS_FLAG)
        darkColor.setColor(Color.argb(255, 115, 67, 67))
        lightColor.setColor(Color.argb(150, 195, 176, 176))
        playerOneCoin.setColor(Color.argb(255, 32, 32, 32)) //blue
        playerTwoCoin.setColor(Color.argb(255, 119, 0, 0)) //green
        _black = Paint(Paint.ANTI_ALIAS_FLAG)
        _black.setColor(Color.argb(255, 0, 0, 0))
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
            if(fromCoin.player==nextMove)
               // if(IsAnyMoveToGetPoint(fromCoin,row,col))
                    moveCoin(row, col)

            invalidate()
            return true
        } else if (event.actionMasked == MotionEvent.ACTION_MOVE) {

            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun IsAnyMoveToGetPoint(fromCoin: DraughtsCoins, row: Int, col: Int): Boolean {
        var isval:Boolean=true
        if(nextMove==Players.PlayerOne) {
            for (p1 in coinPosition) //p1
            {
                for (p2 in coinPosition) //p2
                {
                    if(p1.player==Players.PlayerOne && p2.player==Players.PlayerTwo)
                    {
                        isval =p2.colum.equals(p1.colum - 1) && (p2.row.equals(p1.row - 1)||p2.row.equals(p1.row + 1))
                            if(isval){
                                var isValTwo:Boolean = col.equals(p1.colum-2) && (row.equals(p1.row-2))
                                //||p2.row.equals(p1.row+2))
                                if(isValTwo&&fromCoin.row==p1.row&&fromCoin.colum==p1.colum)
                                    return true
                            return false
                            }
                    }

                }
            }
        }else{

        }
        return true
    }

    public fun initializeCoins() {
        coinPosition.clear()
        playerOneCoinNumber=12
        playerTwoCoinNumber=12
        nextMove=Players.PlayerOne

        for (j in 0 until 3) {
            for (i in 0 until 8) {
                colourPosition = if ((j + i) % 2 == 1) darkColor else lightColor
                if (colourPosition == darkColor) {
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
                colourPosition = if ((j + i) % 2 == 1) darkColor else lightColor
                if (colourPosition == darkColor) {
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
        var isKingBit:Boolean=fromCoin.isKing
        if(fromCoin.row != row || fromCoin.colum != col){
            if(isCoinCanMove(fromCoin,row,col))
            {
                isMove=true
                val eventp1: DraughtsCoins? = coinPosition.find { it.row == row && it.colum==col}
                if (eventp1 != null) {
                    isMove=false
                }

            }else{
                if(isGetPointMove(fromCoin,row,col)){
                    isMove=false
                    val event: DraughtsCoins? = coinPosition.find { it.row == row && it.colum==col}
                    if(event!=null)
                        isMove=false
                    else{
                        if(fromCoin.isKing) {
                            val eventRight: DraughtsCoins? =
                                coinPosition.find { it.row == fromCoin.row - 1 && it.colum == fromCoin.colum - 1 && row + 1 == it.row && col + 1 == it.colum && fromCoin.player != it.player }
                            if (eventRight != null) {
                                isMove = true
                                //coinPosition.remove(eventRight)
                                removeCoin(eventRight)

                            }
                            val eventLeft: DraughtsCoins? =
                                coinPosition.find { fromCoin.row + 1 == it.row && fromCoin.colum - 1 == it.colum && row - 1 == it.row && col + 1 == it.colum && fromCoin.player != it.player }
                            if (eventLeft != null) {
                                isMove = true
                                //coinPosition.remove(eventLeft)
                                removeCoin(eventLeft)
                            }
                            val eventRightPt: DraughtsCoins? =
                                coinPosition.find { it.row == fromCoin.row + 1 && it.colum == fromCoin.colum + 1 && row - 1 == it.row && col - 1 == it.colum && fromCoin.player != it.player }
                            if (eventRightPt != null) {
                                isMove = true
                                //coinPosition.remove(eventRightPt)
                                removeCoin(eventRightPt)
                            }
                            val eventLeftPt: DraughtsCoins? =
                                coinPosition.find { fromCoin.row - 1 == it.row && fromCoin.colum + 1 == it.colum && row + 1 == it.row && col - 1 == it.colum && fromCoin.player != it.player }
                            if (eventLeftPt != null) {
                                isMove = true
                                //coinPosition.remove(eventLeftPt)
                                removeCoin(eventLeftPt)
                            }

                        }
                        else {
                            if (fromCoin.player == Players.PlayerOne) {
                                val eventRight: DraughtsCoins? =
                                    coinPosition.find { it.row == fromCoin.row - 1 && it.colum == fromCoin.colum - 1 && row + 1 == it.row && col + 1 == it.colum && fromCoin.player != it.player }
                                if (eventRight != null) {
                                    isMove = true
                                    removeCoin(eventRight)
                                    //coinPosition.remove(eventRight)
                                }
                                val eventLeft: DraughtsCoins? =
                                    coinPosition.find { fromCoin.row + 1 == it.row && fromCoin.colum - 1 == it.colum && row - 1 == it.row && col + 1 == it.colum && fromCoin.player != it.player }
                                if (eventLeft != null) {
                                    isMove = true
                                    removeCoin(eventLeft)
                                    //coinPosition.remove(eventLeft)
                                }
                            } else {
                                val eventRightPt: DraughtsCoins? =
                                    coinPosition.find { it.row == fromCoin.row + 1 && it.colum == fromCoin.colum + 1 && row - 1 == it.row && col - 1 == it.colum && fromCoin.player != it.player }
                                if (eventRightPt != null) {
                                    isMove = true
                                    removeCoin(eventRightPt)
                                    //coinPosition.remove(eventRightPt)
                                }
                                val eventLeftPt: DraughtsCoins? =
                                    coinPosition.find { fromCoin.row - 1 == it.row && fromCoin.colum + 1 == it.colum && row + 1 == it.row && col - 1 == it.colum && fromCoin.player != it.player }
                                if (eventLeftPt != null) {
                                    isMove = true
                                    removeCoin(eventLeftPt)
                                    //coinPosition.remove(eventLeftPt)
                                }
                            }
                        }
                    }
                }
            }

            if(isMove&&fromCoin.player==Players.PlayerOne){
                nextMove=Players.PlayerTwo
            }

            if(isMove&&fromCoin.player==Players.PlayerTwo){
                nextMove=Players.PlayerOne
            }
            if(isMove) {
                if(fromCoin.player==Players.PlayerOne && newColum==0){
                    isKingBit=true
                }else if(fromCoin.player==Players.PlayerTwo && newColum==7){
                    isKingBit=true
                }

                coinPosition.remove(fromCoin)

                var newObj: DraughtsCoins = DraughtsCoins(
                    colum = newColum.toInt(),
                    row = newRow.toInt(),
                    player = fromCoin.player,
                    colour = fromCoin.colour,
                    isKing = isKingBit
                )
                coinPosition.add(newObj)

                changeListner?.onNextMovePlayer(nextMove.toString())
            }
        }
    }

    private fun isGetPointMove(frmCoinData: DraughtsCoins, toRow: Int, toColumn: Int): Boolean {
        var clumndrawcheck:Boolean = false
        if(frmCoinData.isKing)
        {
            if((toColumn.equals(frmCoinData.colum-2) && (toRow.equals(frmCoinData.row-2)||toRow.equals(frmCoinData.row+2))&&(toRow>=0&&toRow<=7&&toColumn>=0&&toColumn<=7))||(toColumn.equals(frmCoinData.colum+2) && (toRow.equals(frmCoinData.row-2)||toRow.equals(frmCoinData.row+2))&&(toRow>=0&&toRow<=7&&toColumn>=0&&toColumn<=7)))
                return true
        }
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
        if(frmCoinData.isKing)
        {
            if((toColumn.equals(frmCoinData.colum-1) && (toRow.equals(frmCoinData.row-1)||toRow.equals(frmCoinData.row+1))&&(toRow>=0&&toRow<=7&&toColumn>=0&&toColumn<=7))||(toColumn.equals(frmCoinData.colum+1) && (toRow.equals(frmCoinData.row-1)||toRow.equals(frmCoinData.row+1))&&(toRow>=0&&toRow<=7&&toColumn>=0&&toColumn<=7)))
                return true
        }
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
                colourPosition = if ((j + i) % 2 == 1) darkColor else lightColor


                canvas?.drawRect(
                    j * cellSize,
                    i * cellSize,
                    (j + 1) * cellSize,
                    (i + 1) * cellSize,
                    colourPosition
                )
               // var text = "("+i+"-"+j+")"
              // canvas.drawText(text, j*1.0f* cellSize , (i + 1) * cellSize, _black)

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
           // var text = "["+k+"-"+m+"]"

            canvas?.drawCircle(0.0f, 0.0f, cellSize / 3.0f, obj.colour)
            canvas?.drawCircle(0.0f, 0.0f, cellSize / 5.0f, lightColor)
            canvas?.drawCircle(0.0f, 0.0f, cellSize / 7.0f, obj.colour)
            //canvas.drawText(text, j , i, _black)
            canvas?.restore()
            if(obj.isKing==true){
                drawKing(canvas,cellSize/1.5f,cellSize/1.5f, (obj.row * cellSize)+cellSize/6f ,(obj.colum * cellSize)+cellSize/6f )
            }
        }
    }


    private fun drawKing(canvas:Canvas,w:Float, h:Float,dx:Float,dy:Float){

         val paintCK = Paint()
         val lightColor = Color.parseColor("#EEEEEE")
        paintCK.color=lightColor
         val path = Path()
         val mtx = Matrix()
         var od = 0f
        // crown drawing original size
        val ow = 200f
        val oh = 200f
        od = if (w / ow < h / oh) w / ow else h / oh
        canvas.save()
        canvas.translate((w - od * ow) / 2f + dx, (h - od * oh) / 2f + dy)

        mtx.reset()
        mtx.setScale(od, od)

        //canvas.save()
        //canvas.scale(1.0f,1.0f)
        //canvas.save()
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
        canvas.drawPath(path, paintCK)
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
        //canvas.drawPath(path, paintCK)
        canvas.restore()
    }

    public fun removeCoin(removeobject:DraughtsCoins){
        if(removeobject.player==Players.PlayerOne)
            playerOneCoinNumber=playerOneCoinNumber-1

        if(removeobject.player==Players.PlayerTwo)
            playerTwoCoinNumber=playerTwoCoinNumber-1

        changeListner?.onChange(playerOneCoinNumber,playerTwoCoinNumber)

        coinPosition.remove(removeobject)
    }

    public fun setOnChangeListnerInDraughtView(listner: OnChangeListnerFromDraughtView){
        changeListner=listner
    }

    interface OnChangeListnerFromDraughtView{
        public fun onChange(p1:Int,p2:Int)
        public fun onNextMovePlayer(p1: String)
    }
}