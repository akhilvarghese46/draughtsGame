package com.assignment.md_draughts

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var draughtView: DraughtsView
    private lateinit var resetData: Button
    private lateinit var balanceP1: TextView
    private lateinit var balanceP2: TextView
    private lateinit var nextPlayerData: TextView
    private lateinit var settingsData: Button
    public lateinit var lightBoxColor: Paint
    public lateinit var darkBoxColor: Paint
    public lateinit var playerOneColor: Paint
    public lateinit var playerTwoColor: Paint


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //darkspinner = findViewById<Spinner>(R.id.spin_darkColor)
        //lightspinner = findViewById<Spinner>(R.id.spin_lightColor)
        draughtView = findViewById<DraughtsView>(R.id.draught_view)
        balanceP1 = findViewById<TextView>(R.id.balanceNum_PlayerOne)
        balanceP2 = findViewById<TextView>(R.id.balanceNum_Playertwo)
        nextPlayerData = findViewById<TextView>(R.id.nextPlayer)
        resetData = findViewById<Button>(R.id.btn_reset)
        settingsData = findViewById<Button>(R.id.btn_setting)
        lightBoxColor=draughtView.lightColor
        darkBoxColor=draughtView.darkColor
        playerOneColor=draughtView.playerOneCoin
        playerTwoColor=draughtView.playerTwoCoin

        draughtView.setOnChangeListnerInDraughtView(object :DraughtsView.OnChangeListnerFromDraughtView{
            override fun onChange(p1:Int,p2:Int) {
                balanceP1.text = p1.toString()
                balanceP2.text = p2.toString()
            }

            override fun onNextMovePlayer(p1: String) {
                if(p1==Players.PlayerOne.toString())
                {
                    nextPlayerData.text = "Player 1"
                }else{
                    nextPlayerData.text = "Player 2"
                }
            }
        })

        resetData.setOnClickListener {
            draughtView.initializeCoins()
            draughtView.invalidate()
            balanceP1.text = "12"
            balanceP2.text = "12"
            nextPlayerData.text = "Player 1"
        }

        settingsData.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("NameData", "Akhilllllll")
            //intent.putExtra("lightBoxColor", lightBoxColor.toString())
            /*intent.putExtra("darkBoxColor", darkBoxColor.toString())
            intent.putExtra("playerOneColor", playerOneColor.toString())
            intent.putExtra("playerTwoColor", playerTwoColor.toString())*/
            startActivityForResult(intent, 16)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 16 && resultCode == RESULT_OK) {

            var playerOne : String? =data?.getStringExtra("playerOne")
            var playerTwo : String? =data?.getStringExtra("playerTwo")
            var darkColor : String? =data?.getStringExtra("darkColor")
            var lightColor : String? =data?.getStringExtra("lightColor")

            if(playerOne!=null) {
                draughtView.playerOneCoin.setColor(playerOne.toInt())
                playerOneColor.setColor(playerOne.toInt())
            }
            if(playerTwo!=null) {
                draughtView.playerTwoCoin.setColor(playerTwo.toInt())
                playerTwoColor.setColor(playerTwo.toInt())
            }

            if(darkColor!=null) {
                draughtView.darkColor.setColor(darkColor.toInt())
                darkBoxColor.setColor(darkColor.toInt())
            }
            if(lightColor!=null) {
                draughtView.lightColor.setColor(lightColor.toInt())
                lightBoxColor.setColor(lightColor.toInt())
            }
            draughtView.invalidate()
        }
// call the superclass version of this as required
        super.onActivityResult(requestCode, resultCode, data)
    }

     fun selectDarkColours(argb: Int) {
         //draughtView.darkColor =   Color.parseColor("#F1CBBF")
     }
}