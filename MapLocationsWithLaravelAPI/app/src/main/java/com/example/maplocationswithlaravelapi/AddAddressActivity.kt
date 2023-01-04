package com.example.maplocationswithlaravelapi

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.maplocationswithlaravelapi.Config.Companion.BASE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.io.IOException

class AddAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        //enables the back arrow on the action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

       var address = findViewById(R.id.address) as EditText
       var zipcode = findViewById(R.id.zipcode) as EditText
       var addressDescription = findViewById(R.id.address_description) as EditText
       var login = findViewById(R.id.login) as Button

        login.setOnClickListener {

            /* if condition is not met, it will exit the listener*/
            if (address.length() == 0) {
                address.setError("This field is required")
                return@setOnClickListener
            }
            if (zipcode.length() == 0) {
                zipcode.setError("This field is required");
                return@setOnClickListener
            }
            if (addressDescription.length() == 0) {
                addressDescription.setError("This field is required");
                return@setOnClickListener
            }

            val geocoder = Geocoder(this)
            var latitude: String? = null
            var longitude: String? = null
            val myZipcode = zipcode.getText().toString()
            val myAddress = address.getText().toString()
            val myAddressDescription = addressDescription.getText().toString()

            /* get latitude and longitude of an address using geocoder */
            try {
                val addresses: List<Address>? =
                    geocoder.getFromLocationName(myAddress + ',' + myZipcode, 1)
                if (addresses != null && !addresses.isEmpty()) {
                    val address: Address = addresses[0]
                    latitude = address.getLatitude().toString()
                    longitude = address.getLongitude().toString()
                } else {
                    // Display this message when Geocoder services are not available
                    // or when your address's Latitude and Longitude is not found
                    Toast.makeText(
                        this,
                        "Unable to geocode the address \n Try with a different address ",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
            } catch (e: IOException) {
                // handle exception
            }

            // Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build()

            val service = retrofit.create(APIService::class.java)

            val params = HashMap<String?, String?>()
            params["address"] = myAddress
            params["city"] = "-"
            params["state"] = "-"
            params["zipcode"] = myZipcode
            params["description"] = myAddressDescription
            params["latitude"] = latitude
            params["longitude"] = longitude

            CoroutineScope(Dispatchers.IO).launch {

                //  POST request and get response
                val response = service.createMapLocation(params)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(getApplicationContext(), " Address Successfuly Submitted \n to The Laravel API / BackEnd ", Toast.LENGTH_SHORT)
                            .show();
                    } else {
                        Log.e("ERROR - Try Again", response.code().toString())
                        Toast.makeText(getApplicationContext(), " Error - Check your internet connection. ", Toast.LENGTH_SHORT)
                            .show();
                    }
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