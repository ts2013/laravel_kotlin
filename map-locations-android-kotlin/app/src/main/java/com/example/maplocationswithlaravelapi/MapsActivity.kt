package com.example.maplocationswithlaravelapi

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.maplocationswithlaravelapi.databinding.ActivityMapsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMapsBinding.inflate(layoutInflater)
     setContentView(binding.root)

        //enables the back arrow on the action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {

                try {
                    val result = DataApi.retrofitService.getMapLocations()

                    for (i in 0 until result.size) {
                        val newLocation = LatLng(
                            result[i].addLocLat.toDouble(),
                            result[i].addLocLon.toDouble()
                        )

                        mMap.addMarker(
                            MarkerOptions().position(newLocation)
                                .title(result[i].description)
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation))
                        val location = CameraUpdateFactory.newLatLngZoom(
                            newLocation, 5f
                        )
                        mMap.animateCamera(location)
                    }

                } catch (e: Exception) {
                    // Do something...
                    Log.v(ContentValues.TAG, "ERROR--" + e.toString())
                }
            }
        }
    }

    /* call this activity using the back arrow on the action bar */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MenuActivity::class.java)
                // start your next activity
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}