package com.assignment.md_draughts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var draughtView: DraughtsView
    private lateinit var resetData: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetData = findViewById<Button>(R.id.btn_reset)


        resetData.setOnClickListener {
            draughtView = findViewById<DraughtsView>(R.id.draught_view)
            draughtView.initializeCoins()
            draughtView.invalidate()
        }
    }
}