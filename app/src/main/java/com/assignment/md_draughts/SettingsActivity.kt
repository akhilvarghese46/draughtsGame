package com.assignment.md_draughts

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity()  {
    private lateinit var selectDarkColr: Button
    private lateinit var selectLightColr: Button
    private lateinit var selectPlayerOne: Button
    private lateinit var selectPlayerTwo: Button

    private var sBarAlpha: SeekBar? = null
    private var sBarRed: SeekBar? = null
    private var sBarGreen: SeekBar? = null
    private var sBarBlue: SeekBar? = null
    private lateinit var txtcolor: TextView
    private lateinit var btnSubmitColur: Button
    lateinit var darkColor:String
    lateinit var lightColor:String
    lateinit var playerOne:String
    lateinit var playerTwo:String


    var alphaVal:Int =0
    var redVal:Int =0
    var greenVal:Int =0
    var blueVal:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

var NameData = intent.getStringExtra("NameData")
        darkColor = intent.getStringExtra("darkBoxColor").toString()
        lightColor = intent.getStringExtra("lightBoxColor").toString()
        playerOne = intent.getStringExtra("playerOneColor").toString()
        playerTwo = intent.getStringExtra("playerTwoColor").toString()

        selectDarkColr = findViewById<Button>(R.id.btn_darkColor)
        selectLightColr = findViewById<Button>(R.id.btn_lightColor)
        selectPlayerOne = findViewById<Button>(R.id.btn_CoinOneClr)
        selectPlayerTwo = findViewById<Button>(R.id.btn_CoinTwoClr)

        selectDarkColr.setBackgroundColor(darkColor.toInt())
        selectLightColr.setBackgroundColor(lightColor.toInt())
        selectPlayerOne.setBackgroundColor(playerOne.toInt())
        selectPlayerTwo.setBackgroundColor(playerTwo.toInt())

        selectDarkColr.setOnClickListener {
            openColourFlterBox("Dark")
        }

        selectLightColr.setOnClickListener {
            openColourFlterBox("Light")
        }

        selectPlayerOne.setOnClickListener {
            openColourFlterBox("p1")
        }

        selectPlayerTwo.setOnClickListener {
            openColourFlterBox("p2")
        }
    }


    override fun onBackPressed() {
        // your code.
        var result: Intent = Intent(Intent.ACTION_VIEW)
        result.putExtra("playerOne",playerOne.toString())
        result.putExtra("playerTwo", playerTwo.toString())
        result.putExtra("darkColor",darkColor.toString())
        result.putExtra("lightColor", lightColor.toString())
        setResult(RESULT_OK, result)
        finish()
    }
    private fun openColourFlterBox(btn:String) {
        val colourFilterDiloag = LayoutInflater.from(this).inflate(R.layout.colourfilter, null )
        val colour_Builder = AlertDialog.Builder(this).setView(colourFilterDiloag)

        val mAlertDialog = colour_Builder.show()


        sBarAlpha = colourFilterDiloag.findViewById<View>(R.id.Alpha) as SeekBar
        sBarRed = colourFilterDiloag.findViewById<View>(R.id.Red) as SeekBar
        sBarGreen = colourFilterDiloag.findViewById<View>(R.id.Green) as SeekBar
        sBarBlue = colourFilterDiloag.findViewById<View>(R.id.Blue) as SeekBar
        txtcolor = colourFilterDiloag.findViewById<View>(R.id.colourBox) as TextView
        btnSubmitColur = colourFilterDiloag.findViewById<View>(R.id.btnClor) as Button

        selectALlColours()
        btnSubmitColur.setOnClickListener {
            mAlertDialog.dismiss()
            if(btn=="Dark"){
                selectDarkColr.setBackgroundColor(Color.argb(alphaVal, redVal, greenVal, blueVal))
                darkColor=Color.argb(alphaVal, redVal, greenVal, blueVal).toString()
            }
            if(btn=="Light") {
                selectLightColr.setBackgroundColor(Color.argb(alphaVal, redVal, greenVal, blueVal))
                lightColor=Color.argb(alphaVal, redVal, greenVal, blueVal).toString()
            }
            if(btn=="p1") {
                selectPlayerOne.setBackgroundColor(Color.argb(alphaVal, redVal, greenVal, blueVal))
                playerOne=Color.argb(alphaVal, redVal, greenVal, blueVal).toString()
            }

            if(btn=="p2") {
                selectPlayerTwo.setBackgroundColor(Color.argb(alphaVal, redVal, greenVal, blueVal))
                playerTwo = Color.argb(alphaVal, redVal, greenVal, blueVal).toString()
            }

        }
    }

    private fun selectALlColours() {
        alphaVal =0
        redVal =0
        greenVal =0
        blueVal =0
        sBarAlpha!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var pval = 0
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pval = progress
                alphaVal=pval
                createNewColours()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //write custom code to on start progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //tView!!.text = pval.toString() + "/" + seekBar.max
                alphaVal=pval
                createNewColours()
            }
        })
//Red
        sBarRed!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var pval = 0
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pval = progress
                redVal=pval
                createNewColours()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //write custom code to on start progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                redVal=pval
                createNewColours()
            }
        })


//Green
        sBarGreen!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var pval = 0
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pval = progress
                greenVal=pval
                createNewColours()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //write custom code to on start progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                greenVal=pval
                createNewColours()
            }
        })

//Blue

        sBarBlue!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var pval = 0
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pval = progress
                blueVal=pval
                createNewColours()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //write custom code to on start progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                blueVal=pval
                createNewColours()
            }
        })
    }

    private fun createNewColours() {
        txtcolor.setBackgroundColor(Color.argb(alphaVal, redVal, greenVal, blueVal))

    }



}