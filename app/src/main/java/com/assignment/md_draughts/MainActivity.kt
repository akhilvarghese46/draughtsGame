package com.assignment.md_draughts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var draughtView: DraughtsView
    private lateinit var resetData: Button
    private lateinit var balanceP1: TextView
    private lateinit var balanceP2: TextView
    private var darkspinner: Spinner? = null
    private var lightspinner: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        darkspinner = findViewById<Spinner>(R.id.spin_darkColor)
        lightspinner = findViewById<Spinner>(R.id.spin_lightColor)
        draughtView = findViewById<DraughtsView>(R.id.draught_view)
        balanceP1 = findViewById<Button>(R.id.balanceNum_PlayerOne)
        balanceP2 = findViewById<Button>(R.id.balanceNum_Playertwo)
        resetData = findViewById<Button>(R.id.btn_reset)


        draughtView.setOnChangeListner(object :DraughtsView.OnChangeListner{
            override fun onChange(p1:Int,p2:Int) {

                balanceP1.text = p1.toString()
                balanceP2.text = p2.toString()
            }

        })




        var adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_DarkColour,
            android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        darkspinner?.setAdapter(adapter)

        var adapterlight: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_lightColour,
            android.R.layout.simple_spinner_item)

        adapterlight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        lightspinner?.setAdapter(adapterlight)

        resetData.setOnClickListener {

            draughtView.initializeCoins()
            draughtView.invalidate()
        }


        darkspinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2==0)
                    draughtView.darkColor = Color.parseColor("#4C1706")
                if(p2==1)
                    draughtView.darkColor = Color.parseColor("#000000")
                if(p2==2)
                    draughtView.darkColor = Color.parseColor("#005904")
                draughtView.invalidate()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })

        lightspinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2==0)
                    draughtView.lightColor = Color.parseColor("#F1CBBF")
                if(p2==1)
                    draughtView.lightColor = Color.parseColor("#FFFFFFFF")
                if(p2==2)
                    draughtView.lightColor = Color.parseColor("#C8E8A2")
                draughtView.invalidate()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })



    }
}