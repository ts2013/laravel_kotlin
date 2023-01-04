package com.example.maplocationswithlaravelapi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var addAddressBtn = findViewById(R.id.addAddressBtn) as Button
        var showMapBtn = findViewById(R.id.showMapBtn) as Button


        addAddressBtn.setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        showMapBtn.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}