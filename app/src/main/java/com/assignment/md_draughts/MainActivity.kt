package com.assignment.md_draughts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    private lateinit var draughtView: DraughtsView
    private lateinit var resetData: Button
    private var darkspinner: Spinner? = null
    private var lightspinner: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        darkspinner = findViewById<Spinner>(R.id.spin_darkColor)
        lightspinner = findViewById<Spinner>(R.id.spin_lightColor)
        draughtView = findViewById<DraughtsView>(R.id.draught_view)

        resetData = findViewById<Button>(R.id.btn_reset)

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